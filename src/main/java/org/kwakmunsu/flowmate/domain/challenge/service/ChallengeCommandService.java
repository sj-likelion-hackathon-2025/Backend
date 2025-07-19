package org.kwakmunsu.flowmate.domain.challenge.service;

import static org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus.APPROVED;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApplication;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeParticipant;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeRole;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant.ChallengeParticipantRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.ChallengeApplicationRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication.ChallengeApplicationApprovalServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication.ChallengeApplicationServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.global.exception.DuplicationException;
import org.kwakmunsu.flowmate.global.exception.UnAuthenticationException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeCommandService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;
    private final ChallengeApplicationRepository challengeApplicationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(ChallengeCreateServiceRequest request) {
        Challenge challenge = Challenge.create(request.toDomainRequest());
        challengeRepository.save(challenge);

        Member member = memberRepository.findById(request.memberId());

        // 생성한 멤버는 해당 챌린지의 초대 리더로 부임한다.
        ChallengeParticipant participant = ChallengeParticipant.create(member, challenge, ChallengeRole.LEADER);
        challengeParticipantRepository.save(participant);

        return challenge.getId();
    }

    public Long apply(ChallengeApplicationServiceRequest request) {
        Member member = memberRepository.findById(request.memberId());
        Challenge challenge = challengeRepository.findById(request.challengeId());

        validateDuplicateApplication(request);
        validateApplicationCapacity(challenge);

        ChallengeApplication challengeApplication = ChallengeApplication.create(member, challenge.getId(), request.message());
        return challengeApplicationRepository.save(challengeApplication);
    }

    @Transactional
    public void approval(ChallengeApplicationApprovalServiceRequest request) {
        validateLeader(request.challengeId(), request.leaderId());

        ChallengeApplication application = challengeApplicationRepository.findById(request.applicationId());

        // 승인이라면 신청 상태를 승인으로 바꾸고 챌린지에 참가자로 추가한다. 거절이라면 신청 상태만 거절로 바꾼다.
        processApplicationDecision(request.status(), application);
    }

    private void validateDuplicateApplication(ChallengeApplicationServiceRequest request) {
        if (challengeApplicationRepository.existsMemberIdAndChallengeId(request.memberId(), request.challengeId())) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_APPLICATION);
        }
    }

    private void validateApplicationCapacity(Challenge challenge) {
        int maxApplicantsCount = (int) (challenge.getMaxParticipants() * 2); // 신청 최대 인원 =  챌린지 최대 인원 수 * 2
        Integer count = challengeApplicationRepository.countApplicantsWithLimit(challenge.getId(), maxApplicantsCount);

        if (count < maxApplicantsCount) {
            return;
        }
        throw new DuplicationException(ErrorStatus.OVER_CAPACITY_APPLICATION);
    }

    private void validateLeader(Long challengeId, Long memberId) {
        if (challengeRepository.existsByIdAndLeaderId(challengeId, memberId)) {
            return;
        }
        throw new UnAuthenticationException(ErrorStatus.UNAUTHORIZED_ERROR);
    }

    private void processApplicationDecision(ApprovalStatus status, ChallengeApplication application) {
        try {
            if (status == APPROVED) {
                application.approve();
                addParticipant(application);
            } else {
                application.reject();
            }
        } catch (IllegalStateException e) {
            throw new DuplicationException(ErrorStatus.ALREADY_DECISION_APPLICATION);
        }
    }

    private void addParticipant(ChallengeApplication application) {
        Member member = memberRepository.findById(application.getMember().getId());
        Challenge challenge = challengeRepository.findById(application.getChallengeId());
        ChallengeParticipant participant = ChallengeParticipant.create(member, challenge, ChallengeRole.GENERAL);
        challengeParticipantRepository.save(participant);
    }

}
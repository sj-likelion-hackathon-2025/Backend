package org.kwakmunsu.flowmate.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApply;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeParticipant;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeRole;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant.ChallengeParticipantRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository.ChallengeApplyRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.ChallengeApplyServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.global.exception.DuplicationException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeCommandService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;
    private final ChallengeApplyRepository challengeApplyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(ChallengeCreateServiceRequest request) {
        Challenge challenge = Challenge.create(request.toDomainRequest());
        challengeRepository.save(challenge);

        Member member = memberRepository.findById(request.memberId());

        // 생성한 멤버는 해당 챌린지의 초대 리더로 부임한다.
        ChallengeParticipant participant = ChallengeParticipant.create(member, challenge, ChallengeRole.LEADER);
        challengeParticipantRepository.save(participant);
    }

    public void apply(ChallengeApplyServiceRequest request) {
        Member member = memberRepository.findById(request.memberId());
        Challenge challenge = challengeRepository.findById(request.challengeId());

        validateDuplicateApplication(request);
        validateApplicationCapacity(challenge);

        ChallengeApply challengeApply = ChallengeApply.create(member, challenge.getId(), request.message());
        challengeApplyRepository.save(challengeApply);
    }

    private void validateDuplicateApplication(ChallengeApplyServiceRequest request) {
        if (challengeApplyRepository.existsMemberIdAndChallengeId(request.memberId(), request.challengeId())) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_APPLY);
        }
    }

    private void validateApplicationCapacity(Challenge challenge) {
        int maxApplicantsCount = (int) (challenge.getMaxParticipants() * 2);
        Integer count = challengeApplyRepository.countApplicantsWithLimit(challenge.getId(), maxApplicantsCount);

        if (count < maxApplicantsCount) {
            return;
        }
        throw new DuplicationException(ErrorStatus.OVER_CAPACITY_CHALLENGE);
    }

}
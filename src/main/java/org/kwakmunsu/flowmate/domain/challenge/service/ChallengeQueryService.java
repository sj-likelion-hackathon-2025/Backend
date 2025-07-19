package org.kwakmunsu.flowmate.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.ChallengeApplicationRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationListResponse;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.kwakmunsu.flowmate.global.exception.UnAuthenticationException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeQueryService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeApplicationRepository challengeApplicationRepository;

    public ChallengeListResponse readAll(ChallengeReadServiceRequest request) {
        return challengeRepository.findAll(request.toDomainRequest());
    }

    public ChallengeApplicationListResponse readApplyList(Long challengeId, Long memberId) {
        validateLeader(challengeId, memberId);

        return challengeApplicationRepository.findByChallengeIdAndStatus(challengeId, ApprovalStatus.PENDING);
    }

    private void validateLeader(Long challengeId, Long memberId) {
        if(challengeRepository.existsByIdAndLeaderId(challengeId, memberId)) {
            return;
        }
        throw new UnAuthenticationException(ErrorStatus.UNAUTHORIZED_ERROR);
    }

}
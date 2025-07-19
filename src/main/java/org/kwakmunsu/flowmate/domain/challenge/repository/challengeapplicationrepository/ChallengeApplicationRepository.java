package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApplication;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationListResponse;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationResponse;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeApplicationRepository {

    private final ChallengeApplicationJpaRepository challengeApplicationJpaRepository;

    public Integer countApplicantsWithLimit(Long challengeId, int maxApplicantsCount) {
        return challengeApplicationJpaRepository.countApplicantsWithLimit(challengeId, maxApplicantsCount);
    }

    public boolean existsMemberIdAndChallengeId(Long memberId, Long challengeId) {
        return challengeApplicationJpaRepository.existsByMemberIdAndChallengeId(memberId, challengeId);
    }

    public void save(ChallengeApplication challengeApplication) {
        challengeApplicationJpaRepository.save(challengeApplication);
    }

    public ChallengeApplicationListResponse findByChallengeIdAndStatus(Long challengeId, ApprovalStatus status) {
        List<ChallengeApplicationResponse> responses = challengeApplicationJpaRepository.findByChallengeIdAndStatus(challengeId, status);

        return new ChallengeApplicationListResponse(responses);
    }

}
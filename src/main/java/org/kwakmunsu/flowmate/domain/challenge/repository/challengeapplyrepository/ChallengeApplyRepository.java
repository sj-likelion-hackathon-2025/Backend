package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApply;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeApplyRepository {

    private final ChallengeApplyJpaRepository challengeApplyJpaRepository;

    public Integer countApplicantsWithLimit(Long challengeId, int maxApplicantsCount) {
        return challengeApplyJpaRepository.countApplicantsWithLimit(challengeId, maxApplicantsCount);
    }

    public boolean existsMemberIdAndChallengeId(Long memberId, Long challengeId) {
        return challengeApplyJpaRepository.existsByMemberIdAndChallengeId(memberId, challengeId);
    }

    public void save(ChallengeApply challengeApply) {
        challengeApplyJpaRepository.save(challengeApply);
    }

}
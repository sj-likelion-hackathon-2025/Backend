package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository;

import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChallengeApplyJpaRepository extends JpaRepository<ChallengeApply, Long> {

    @Query(value = "SELECT COUNT(*) FROM (SELECT 1 FROM challenge_apply WHERE challenge_id = :challengeId LIMIT :limitSize) as result",
            nativeQuery = true)
    Integer countApplicantsWithLimit(@Param("challengeId") Long challengeId, @Param("limitSize") int limitSize);

    boolean existsByMemberIdAndChallengeId(Long memberId, Long challengeId);

}

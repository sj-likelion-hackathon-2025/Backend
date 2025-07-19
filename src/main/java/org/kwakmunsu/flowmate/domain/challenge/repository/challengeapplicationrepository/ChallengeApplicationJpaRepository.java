package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository;
import java.util.List;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApplication;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationResponse;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChallengeApplicationJpaRepository extends JpaRepository<ChallengeApplication, Long> {

    @Query(value = "SELECT COUNT(*) FROM (SELECT 1 FROM challenge_application WHERE challenge_id = :challengeId LIMIT :limitSize) as result",
            nativeQuery = true)
    Integer countApplicantsWithLimit(@Param("challengeId") Long challengeId, @Param("limitSize") int limitSize);

    boolean existsByMemberIdAndChallengeId(Long memberId, Long challengeId);

    @Query("select new org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto."
            + "ChallengeApplicationResponse(c.id, m.id, m.name, m.grade, c.message) " +
            "from ChallengeApplication c join c.member m " +
            "where c.challengeId = :challengeId and c.status = :status"
    )
    List<ChallengeApplicationResponse> findByChallengeIdAndStatus(@Param("challengeId") Long challengeId, @Param("status") ApprovalStatus status);

}

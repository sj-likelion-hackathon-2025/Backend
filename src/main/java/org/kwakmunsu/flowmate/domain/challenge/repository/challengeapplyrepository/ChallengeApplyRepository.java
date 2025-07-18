package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApply;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository.dto.ChallengeApplyListResponse;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository.dto.ChallengeApplyResponse;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
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

    public ChallengeApplyListResponse findByChallengeIdAndStatus(Long challengeId, ApprovalStatus status) {
        List<ChallengeApplyResponse> responses = challengeApplyJpaRepository.findByChallengeIdAndStatus(challengeId, status);

        return new ChallengeApplyListResponse(responses);
    }

}
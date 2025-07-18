package org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge;

import lombok.Builder;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeListType;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.SortBy;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeReadDomainRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

@Builder
public record ChallengeReadServiceRequest(
        Long memberId,
        String q,
        SortBy sortBy,
        InterestCategory interestCategory,
        ChallengeListType challengeListType,
        Long lastChallengeId
) {

    public ChallengeReadDomainRequest toDomainRequest() {
        return ChallengeReadDomainRequest.builder()
                .memberId(memberId)
                .q(q)
                .sortBy(sortBy)
                .interestCategory(interestCategory)
                .challengeListType(challengeListType)
                .lastChallengeId(lastChallengeId)
                .build();
    }

}
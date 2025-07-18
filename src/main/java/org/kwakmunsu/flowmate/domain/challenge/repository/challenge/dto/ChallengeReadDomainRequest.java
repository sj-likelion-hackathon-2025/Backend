package org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto;

import lombok.Builder;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeListType;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.SortBy;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

@Builder
public record ChallengeReadDomainRequest(
        Long memberId,
        String q,
        SortBy sortBy,
        InterestCategory interestCategory,
        ChallengeListType challengeListType,
        Long lastChallengeId
) {

}
package org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge;

import static org.kwakmunsu.flowmate.global.util.TimeConverter.stringToDate;

import lombok.Builder;
import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

@Builder
public record ChallengeCreateServiceRequest(
        String title,
        String introduction,
        String category,
        String startDate,
        String endDate,
        String rule,
        Long maxParticipants,
        Long memberId
) {

    public ChallengeCreateDomainRequest toDomainRequest() {
        return ChallengeCreateDomainRequest.builder()
                .title(title)
                .introduction(introduction)
                .category(InterestCategory.valueOf(category))
                .startDate(stringToDate(startDate))
                .endDate(stringToDate(endDate))
                .rule(rule)
                .maxParticipants(maxParticipants)
                .leaderId(memberId)
                .build();
    }

}
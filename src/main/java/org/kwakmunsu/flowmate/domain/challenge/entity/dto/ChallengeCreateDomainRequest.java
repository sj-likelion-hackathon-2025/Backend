package org.kwakmunsu.flowmate.domain.challenge.entity.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

@Builder
public record ChallengeCreateDomainRequest(
        String title,
        String introduction,
        InterestCategory category,
        LocalDate startDate,
        LocalDate endDate,
        String rule,
        Long maxParticipants
) {

}
package org.kwakmunsu.flowmate.domain.challenge.service.dto;

import lombok.Builder;

@Builder
public record ChallengeApplyServiceRequest(
        String message,
        Long challengeId,
        Long memberId
) {

}
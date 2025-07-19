package org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication;

import lombok.Builder;

@Builder
public record ChallengeApplicationServiceRequest(
        String message,
        Long challengeId,
        Long memberId
) {

}
package org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication;

import lombok.Builder;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;

@Builder
public record ChallengeApplicationApprovalServiceRequest(
        ApprovalStatus status,
        Long challengeId,
        Long applicationId,
        Long leaderId
) {

}
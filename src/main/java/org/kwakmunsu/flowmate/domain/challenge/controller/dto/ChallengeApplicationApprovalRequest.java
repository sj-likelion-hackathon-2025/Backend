package org.kwakmunsu.flowmate.domain.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication.ChallengeApplicationApprovalServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.kwakmunsu.flowmate.global.annotation.EnumValid;

@Schema(description = "챌린지 승인 여부 요청 DTO")
public record ChallengeApplicationApprovalRequest(
        @Schema(description = "승인 여부", example = "APPROVED | REJECTED")
        @EnumValid(enumClass = ApprovalStatus.class, message = "유효하지 않은 형식입니다.")
        ApprovalStatus status
) {

    public ChallengeApplicationApprovalServiceRequest toServiceRequest(Long challengeId, Long applicationId, Long leaderId) {
        return ChallengeApplicationApprovalServiceRequest.builder()
                .status(status)
                .challengeId(challengeId)
                .applicationId(applicationId)
                .leaderId(leaderId)
                .build();
    }

}
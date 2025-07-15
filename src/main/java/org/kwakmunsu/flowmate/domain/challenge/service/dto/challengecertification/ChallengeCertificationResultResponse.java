package org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChallengeCertificationResultResponse(
        @Schema(description = "인증 ID", example = "1")
        Long certificationId,

        @Schema(description = "인증 이미지", example = "https://example.com/image.jpg", requiredMode = NOT_REQUIRED)
        String imageUrl,

        @Schema(description = "인증 날짜", example = "2023-10-01")
        String date,

        @Schema(description = "인증 상태", example = "UNVERIFIED | PENDING | APPROVED | REJECTED | ")
        String status
) {

}
package org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챌린저 인증 상세 조회 응답 DTO")
public record ChallengeCertificationDetailResponse(
        @Schema(description = "챌린저 ID", example = "1")
        String challengerId,

        @Schema(description = "챌린저 이름", example = "홍길동")
        String name,

        @Schema(description = "챌린저 등급", example = "ROOKIE")
        String grade,

        @Schema(description = "챌린저 인증 이미지 URL", example = "https://example.com/certification.jpg")
        String imageUrl
) {

}

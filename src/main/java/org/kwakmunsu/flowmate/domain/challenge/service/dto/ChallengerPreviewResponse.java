package org.kwakmunsu.flowmate.domain.challenge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챌린저 미리보기 응답 DTO")
public record ChallengerPreviewResponse(
        @Schema(description = "챌린저 ID", example = "1")
        Long challengerId,

        @Schema(description = "챌린저 이름", example = "장원영")
        String name,

        @Schema(description = "챌린저 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String profileImageUrl,

        @Schema(description = "챌린저 각오", example = "전남친에게 복수하고 싶습니다.")
        String introduction,

        @Schema(description = "인증 참여 여부", example ="UNVERIFIED, VERIFIED, PENDING" )
        String certificationStatus
) {

}

package org.kwakmunsu.flowmate.domain.member.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 정보 응답")
public record MemberInfoResponse(
        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String profileImageUrl,

        @Schema(description = "이름", example = "오다현")
        String name,

        @Schema(description = "등급", example = "ROOKIE")
        String grade,

        @Schema(description = "포인트", example = "1000")
        Long point
) {
}
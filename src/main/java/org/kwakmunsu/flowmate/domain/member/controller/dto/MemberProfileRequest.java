package org.kwakmunsu.flowmate.domain.member.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 정보 등록 요청 DTO")
public record MemberProfileRequest(
        @Schema(description = "회원 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String profileImageUrl,

        @Schema(description = "회원 이름", example = "곽태풍")
        String name
) {
}
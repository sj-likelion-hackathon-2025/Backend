package org.kwakmunsu.flowmate.domain.member.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.kwakmunsu.flowmate.domain.member.entity.Member;

@Schema(description = "회원 정보 응답")
@Builder
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

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .profileImageUrl(member.getProfileImgUrl())
                .name(member.getName())
                .grade(member.getGrade().name())
                .point(member.getPoint())
                .build();
    }

}
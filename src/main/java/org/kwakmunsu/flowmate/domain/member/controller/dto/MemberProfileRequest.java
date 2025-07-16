package org.kwakmunsu.flowmate.domain.member.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "프로필 정보 등록 요청 DTO")
public record MemberProfileRequest(
        @Schema(description = "회원 이름", example = "곽태풍")
        @NotBlank(message = "이름은 필수 입력값입니다.")
        String name
) {

    public MemberProfileServiceRequest toServiceRequest(Long memberId, MultipartFile image) {
        return MemberProfileServiceRequest.builder()
                .name(name)
                .profileImage(image)
                .memberId(memberId)
                .build();
    }

}
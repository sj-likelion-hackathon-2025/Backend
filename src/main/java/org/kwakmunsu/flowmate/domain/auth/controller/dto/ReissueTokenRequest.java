package org.kwakmunsu.flowmate.domain.auth.controller.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.kwakmunsu.flowmate.domain.member.service.dto.ReissueTokenServiceRequest;

@Schema(description = "Access Token 재발급 요청 DTO")
public record ReissueTokenRequest(
        @Schema(description = "refreshToken 토큰", example = "your-refresh-token", requiredMode = REQUIRED)
        @NotBlank(message = "refreshToken은 필수 입력값입니다.")
        String refreshToken
) {

    public ReissueTokenServiceRequest toServiceRequest() {
        return ReissueTokenServiceRequest.builder()
                .refreshToken(refreshToken)
                .build();
    }

}
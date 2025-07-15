package org.kwakmunsu.flowmate.domain.auth.controller.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Access Token 재발급 요청 DTO")
public record ReissueTokenRequest(
        @Schema(description = "refreshToken 토큰", example = "your-refresh-token", requiredMode = REQUIRED)
        String refreshToken

) {

}
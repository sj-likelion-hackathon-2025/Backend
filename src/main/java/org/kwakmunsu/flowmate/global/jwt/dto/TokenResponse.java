package org.kwakmunsu.flowmate.global.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "토큰 재발급 응답 DTO")
@Builder
public record TokenResponse(
        @Schema(description = "새로 발급된 Access Token", example = "new-access-token")
        String accessToken,

        @Schema(description = "새로 발급된 Refresh Token", example = "new-refresh-token")
        String refreshToken
) {

}

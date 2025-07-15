package org.kwakmunsu.flowmate.domain.auth.controller;

import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.INVALID_TOKEN;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.NOT_FOUND_TOKEN;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.UNAUTHORIZED_ERROR;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.kwakmunsu.flowmate.domain.auth.controller.dto.ReissueTokenRequest;
import org.kwakmunsu.flowmate.global.annotation.ApiExceptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Auth Controller", description = "Auth API")
public abstract class AuthDocsController {

    @Operation(
            summary = "Access, Refresh Token 재발급 요청 - JWT O",
            description = "리프레시 토큰을 이용해 새로운 Access Token 및 Refresh Token을 재발급합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Access, Refresh Token 재발급 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TokenResponse.class)
            )
    )
    @ApiExceptions(values = {
            INVALID_TOKEN,
            NOT_FOUND_TOKEN
    })
    public abstract ResponseEntity<TokenResponse> reissue(ReissueTokenRequest request);

    @Operation(summary = "카카오 로그인",
            description = """ 
                    카카오 소셜로그인 인증 성공 시 쿼리 파라미터로 accessToken, refreshToken이 전달됩니다.<br>
                    <ul>
                        <li><b>url 예시</<b>: http://localhost:3000/?accessToken=...&refreshToken=...</li>
                    </ul>
                    """
    )
    @ApiResponse(
            responseCode = "302",
            description = """
                    카카오 로그인 성공 후 리다이렉트<br>
                     <ul>
                        <li><b>신규 회원</b>: 회원 정보 등록 화면으로 이동 </li>
                        <li><b>기존 회원</b>: 메인 페이지 화면으로 이동</li>
                     </ul>
                    """
    )
    @ApiExceptions(values = {
            UNAUTHORIZED_ERROR
    })
    @GetMapping("/oauth2/authorization/kakao")
    public void kakao() {
    }

}
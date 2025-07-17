package org.kwakmunsu.flowmate.domain.auth.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.auth.controller.dto.ReissueTokenRequest;
import org.kwakmunsu.flowmate.domain.auth.service.AuthService;
import org.kwakmunsu.flowmate.global.jwt.dto.TokenResponse;
import org.kwakmunsu.flowmate.security.TestMember;
import org.kwakmunsu.flowmate.security.TestSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AuthService authService;

    @TestMember
    @DisplayName("토큰 재발급에 성공한다")
    @Test
    void reissue() throws Exception {
        ReissueTokenRequest request = new ReissueTokenRequest("valid-refresh-token");
        TokenResponse testTokenResponse = TokenResponse.builder()
                .accessToken("testAccessToken")
                .refreshToken("testRefreshToken")
                .build();
        given(authService.reissue(request.toServiceRequest())).willReturn(testTokenResponse);

        mockMvc.perform(
                        post("/auth/reissue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(testTokenResponse.accessToken()))
                .andExpect(jsonPath("$.refreshToken").value(testTokenResponse.refreshToken()));
    }

    @TestMember
    @DisplayName("토큰값을 보내지 않아 재발급에 실패한다")
    @Test
    void failReissue() throws Exception {
        ReissueTokenRequest request = new ReissueTokenRequest("");
        mockMvc.perform(
                        post("/auth/reissue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

}
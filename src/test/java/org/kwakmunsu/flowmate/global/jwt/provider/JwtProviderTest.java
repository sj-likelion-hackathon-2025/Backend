package org.kwakmunsu.flowmate.global.jwt.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.member.entity.Role;

class JwtProviderTest {

    private JwtProvider jwtProvider;
    private Long memberId;
    private Role role;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider("1233asdasdqweqw3123123asdffsdfasdfasggdd");
        this.memberId = 1L;
        this.role = Role.MEMBER;
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void createTokens() {
        var response = jwtProvider.createTokens(memberId, role);

        assertThat(response).isNotNull();
        assertThat(response.accessToken()).isNotNull();
        assertThat(response.refreshToken()).isNotNull();
    }

    @DisplayName("Authentication을 가져온다")
    @Test
    void getAuthentication() {
        var accessToken = jwtProvider.createTokens(memberId, role).accessToken();

        var authentication = jwtProvider.getAuthentication(accessToken);

        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isNotNull();
    }

    @DisplayName("Authentication을 가져오는데 실패한다.")
    @Test
    void failedGetAuthentication() {
        assertThatThrownBy(() -> jwtProvider.getAuthentication("invalidToken") )
            .isInstanceOf(JwtException.class);
    }

    @DisplayName("토큰 유효성 검증")
    @Test
    void isNotValidateToken() {
        var validToken = jwtProvider.createTokens(memberId, role).accessToken();

        boolean falsResult = jwtProvider.isNotValidateToken(validToken);

        assertThat(falsResult).isFalse();

        boolean trueResult = jwtProvider.isNotValidateToken("invalidToken");

        assertThat(trueResult).isTrue();
    }

}
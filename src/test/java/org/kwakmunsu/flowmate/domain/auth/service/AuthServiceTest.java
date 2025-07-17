package org.kwakmunsu.flowmate.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.SocialType;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.domain.member.service.dto.ReissueTokenServiceRequest;
import org.kwakmunsu.flowmate.global.exception.NotFoundException;
import org.kwakmunsu.flowmate.global.exception.UnAuthenticationException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.kwakmunsu.flowmate.global.jwt.dto.TokenResponse;
import org.kwakmunsu.flowmate.global.jwt.provider.JwtProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    JwtProvider jwtProvider;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    AuthService authService;

    @DisplayName("토큰을 재발급 한다")
    @Test
    void reissue() {
        ReissueTokenServiceRequest request = new ReissueTokenServiceRequest("validRefreshToken");
        TokenResponse tokenResponse = new TokenResponse("newAccessToken", "newRefreshToken");
        Member member = Member.createMember("kwak", "iii148389@gmail.com","12345", SocialType.KAKAO, "imageUrl");
        ReflectionTestUtils.setField(member, "id", 1L);

        given(jwtProvider.isNotValidateToken(request.refreshToken())).willReturn(false);
        given(memberRepository.findByRefreshToken(request.refreshToken())).willReturn(member);
        given(jwtProvider.createTokens(member.getId(), member.getRole())).willReturn(tokenResponse);

        TokenResponse response = authService.reissue(request);

        assertThat(response).isNotNull();
        assertThat(response.accessToken()).isEqualTo(tokenResponse.accessToken());
        assertThat(member.getRefreshToken()).isEqualTo(tokenResponse.refreshToken());
    }

    @DisplayName("유효하지 않은 토큰으로 재발급을 실패한다")
    @Test
    void failReissueWhenInvalidToken() {
        ReissueTokenServiceRequest request = new ReissueTokenServiceRequest("invalidRefreshToken");

        given(jwtProvider.isNotValidateToken(request.refreshToken())).willReturn(true);

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(UnAuthenticationException.class);
    }

    @DisplayName("멤버가 가지고 있는 토큰이 아니여서 재발급을 실패한다")
    @Test
    void failReissueWhenMemberNotExistToken() {
        ReissueTokenServiceRequest request = new ReissueTokenServiceRequest("invalidRefreshToken");

        given(jwtProvider.isNotValidateToken(request.refreshToken())).willReturn(false);
        given(memberRepository.findByRefreshToken(request.refreshToken()))
                .willThrow(new NotFoundException(ErrorStatus.NOT_FOUND_TOKEN));

        assertThatThrownBy(() -> authService.reissue(request))
                .isInstanceOf(NotFoundException.class);
    }

}
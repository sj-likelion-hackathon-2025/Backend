package org.kwakmunsu.flowmate.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.domain.member.service.dto.ReissueTokenServiceRequest;
import org.kwakmunsu.flowmate.global.exception.UnAuthenticationException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.kwakmunsu.flowmate.global.jwt.dto.TokenResponse;
import org.kwakmunsu.flowmate.global.jwt.provider.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    // TODO: 재발급 로직 강화. 블랙 리스트 추가 예정
    @Transactional
    public TokenResponse reissue(ReissueTokenServiceRequest request) {
        if (jwtProvider.isNotValidateToken(request.refreshToken())) {
            throw new UnAuthenticationException(ErrorStatus.INVALID_TOKEN);
        }
        Member member = memberRepository.findByRefreshToken(request.refreshToken());

        TokenResponse tokenResponse = jwtProvider.createTokens(member.getId(), member.getRole());

        member.updateRefreshToken(tokenResponse.refreshToken());

        return tokenResponse;
    }

}
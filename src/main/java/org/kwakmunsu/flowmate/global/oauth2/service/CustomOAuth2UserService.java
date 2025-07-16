package org.kwakmunsu.flowmate.global.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.SocialType;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.global.oauth2.dto.CustomOAuth2Member;
import org.kwakmunsu.flowmate.global.oauth2.userinfo.OAuth2UserInfo;
import org.kwakmunsu.flowmate.global.oauth2.userinfo.impl.KakaoOAuth2UserInfo;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String registrationId = request.getClientRegistration().getRegistrationId();

        try {
            return processOAuth2User(registrationId, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(String registrationId, OAuth2User oauth2User) {
        SocialType socialType = SocialType.from(registrationId);
        OAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(oauth2User.getAttributes());
        Member member = findOrCreateMember(userInfo, socialType);

        return new CustomOAuth2Member(member, oauth2User.getAttributes());
    }

    private Member findOrCreateMember(OAuth2UserInfo userInfo, SocialType socialType) {
        Member member = Member.createMember(
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getSocialId(),
                socialType,
                userInfo.getProfileUrl()
        );

        return memberRepository.findBySocialId(userInfo.getSocialId())
                .map(existingMember -> updateMember(existingMember, userInfo))
                .orElseGet(() -> memberRepository.save(member));
    }

    // 기존에 있던 회원이더라도 이메일이나 닉네임이 변경되었을 수도 있으니 업데이트
    private Member updateMember(Member member, OAuth2UserInfo userInfo) {
        member.updateName(userInfo.getName());
        member.updateEmail(userInfo.getEmail());

        return member;
    }

}
package org.kwakmunsu.flowmate.global.oauth2.userinfo.impl;


import java.util.Map;
import org.kwakmunsu.flowmate.global.oauth2.userinfo.OAuth2UserInfo;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> kakaoProfile;
    private final String email;
    private final String socialId;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.socialId = attributes.get("id").toString();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.email = kakaoAccount.get("email").toString();
        this.kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getProfileUrl() {
        return kakaoProfile.get("profile_image_url").toString();
    }

    @Override
    public String getSocialId() {
        return socialId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return kakaoProfile.get("nickname").toString();
    }

}
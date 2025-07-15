package org.kwakmunsu.flowmate.domain.member.entity;

public enum SocialType {

    KAKAO,
    LOCAL, // ADMIN 전용
    ;

    public static SocialType from(String value) {
        return SocialType.valueOf(value.toUpperCase());
    }
}

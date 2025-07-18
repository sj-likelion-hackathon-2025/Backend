package org.kwakmunsu.flowmate.domain.member.entity;

import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    public static Member createMember(String email) {
        return Member.createMember(email, "iii148389@gmail.com", "123445677", SocialType.KAKAO, "testProfileUrl");
    }

    public static Member createMember() {
        return createMember("kwak");
    }

    public static Member createMember(Long id) {
        Member member = createMember();
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

}
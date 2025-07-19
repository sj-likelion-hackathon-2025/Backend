package org.kwakmunsu.flowmate.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.createMember("kwak", "iii148389@gmail.com", "123445677", SocialType.KAKAO, "testProfileUrl");
    }

    @DisplayName("회원을 생성한다")
    @Test
    void createMember() {
        assertThat(member.getName()).isEqualTo("kwak");
        assertThat(member.getPoint()).isZero();
        assertThat(member.getRole()).isEqualTo(Role.GUEST);
    }

    @DisplayName("회원 기본 정보를 업데이트한다.")
    @Test
    void updateBaseInfo() {
        assertThat(member.getName()).isEqualTo("kwak");

        member.updateBaseInfo("updatedName","testProfileUrl"); // 이름만 업데이트

        assertThat(member.getName()).isEqualTo("updatedName");
        assertThat(member.getProfileImgUrl()).isEqualTo("testProfileUrl");
    }

    @DisplayName("RefreshToken을 업데이트한다")
    @Test
    void updateRefreshToken() {
        assertThat(member.getRefreshToken()).isNull();

        member.updateRefreshToken("newRefreshToken");

        assertThat(member.getRefreshToken()).isEqualTo("newRefreshToken");
    }

    @DisplayName("포인트를 증가한다")
    @Test
    void increasePoint() {
        assertThat(member.getPoint()).isZero();

        member.increasePoint(100L);

        assertThat(member.getPoint()).isEqualTo(100L);
        // 음수가 전달될 시 예외 반환
        assertThatThrownBy(() -> member.increasePoint(-100L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포인트를 감소시킨다.")
    @Test
    void decreasePoint() {
        assertThat(member.getPoint()).isZero();

        member.increasePoint(100L);
        member.decreasePoint(50L);

        assertThat(member.getPoint()).isEqualTo(50L);

        member.decreasePoint(100L);
        assertThat(member.getPoint()).isZero();
    }

    @DisplayName("등급이 업데이트 된다")
    @Test
    void updateGrade() {
        assertThat(member.getGrade()).isEqualTo(Grade.ROOKIE);

        member.increasePoint(1_000L);

        assertThat(member.getGrade()).isEqualTo(Grade.PRO);

        member.decreasePoint(1_00L);

        assertThat(member.getGrade()).isEqualTo(Grade.JUNIOR);
    }

}
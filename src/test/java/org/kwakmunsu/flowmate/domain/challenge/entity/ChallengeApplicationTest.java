package org.kwakmunsu.flowmate.domain.challenge.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;

class ChallengeApplicationTest {

    @DisplayName("챌린지 신청 생성")
    @Test
    void apply() {
        Member member = MemberFixture.createMember();
        Long challengeId = 1L;

        ChallengeApplication apply = ChallengeApplication.create(member, challengeId, "message");

        assertThat(apply.getMember()).isNotNull();
        assertThat(apply.getStatus()).isEqualTo(ApprovalStatus.PENDING);
        assertThat(apply.getChallengeId()).isEqualTo(1L);
    }

    @DisplayName("챌린지 신청 승인 ")
    @Test
    void approve() {
        Member member = MemberFixture.createMember();
        Long challengeId = 1L;

        ChallengeApplication apply = ChallengeApplication.create(member, challengeId, "message");
        apply.approve();
        assertThat(apply.getStatus()).isEqualTo(ApprovalStatus.APPROVED);

        // 한번 승인 또는 거절하면 결과를 바꿀 수 없다.
        assertThatThrownBy(apply::reject)
            .isInstanceOf(IllegalStateException.class);
    }



}
package org.kwakmunsu.flowmate.domain.challenge.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeRole;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;

class ChallengeParticipantTest {

    @DisplayName("챌린지 참여자를 생성한다")
    @Test
    void create() {
        Member member = MemberFixture.createMember();
        ChallengeCreateDomainRequest request = ChallengeFixture.createChallengeCreateDomainRequest();

        Challenge challenge = Challenge.create(request);
        ChallengeParticipant participant = ChallengeParticipant.create(member, challenge, ChallengeRole.LEADER);

        assertThat(participant).isNotNull();
        assertThat(participant.getRole()).isEqualTo(ChallengeRole.LEADER);
    }

}
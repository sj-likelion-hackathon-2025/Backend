package org.kwakmunsu.flowmate.domain.challenge.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

class ChallengeTest {

    @DisplayName("챌린지 생성")
    @Test
    void create() {
        ChallengeCreateDomainRequest request = ChallengeFixture.createChallengeCreateDomainRequest();

        Challenge challenge = Challenge.create(request);

        assertThat(challenge.getTitle()).isEqualTo(request.title());
        assertThat(challenge.getCategory()).isEqualTo(InterestCategory.DIET);
    }

    @DisplayName("챌린지 정보를 수정한다")
    @Test
    void updateChallenge() {
        ChallengeCreateDomainRequest request = ChallengeFixture.createChallengeCreateDomainRequest();

        Challenge challenge = Challenge.create(request);

        challenge.updateIntroduction("Updated Introduction");
        challenge.updateRules("Updated Rule");
        challenge.updateTitle("Updated Challenge Title");

        assertThat(challenge.getIntroduction()).isEqualTo("Updated Introduction");
    }

}
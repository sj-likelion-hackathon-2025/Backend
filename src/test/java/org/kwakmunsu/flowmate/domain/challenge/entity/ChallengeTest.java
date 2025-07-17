package org.kwakmunsu.flowmate.domain.challenge.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kwakmunsu.flowmate.global.util.TimeConverter.stringToDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

class ChallengeTest {

    @DisplayName("챌린지 생성")
    @Test
    void create() {
        ChallengeCreateDomainRequest request = getChallengeCreateDomainRequest();

        Challenge challenge = Challenge.create(request);

        assertThat(challenge.getTitle()).isEqualTo("Test Challenge");
        assertThat(challenge.getCategory()).isEqualTo(InterestCategory.DIET);
    }

    @DisplayName("챌린지 정보를 수정한다")
    @Test
    void updateChallenge() {
        ChallengeCreateDomainRequest request = getChallengeCreateDomainRequest();

        Challenge challenge = Challenge.create(request);

        challenge.updateIntroduction("Updated Introduction");
        challenge.updateRules("Updated Rule");
        challenge.updateTitle("Updated Challenge Title");

        assertThat(challenge.getIntroduction()).isEqualTo("Updated Introduction");
    }

    private  ChallengeCreateDomainRequest getChallengeCreateDomainRequest() {
        return ChallengeCreateDomainRequest.builder()
                .title("Test Challenge")
                .introduction("Test Introduction")
                .category(InterestCategory.valueOf("DIET"))
                .startDate(stringToDate("2023-01-01"))
                .endDate(stringToDate("2023-01-31"))
                .rule("Test Rule")
                .maxParticipants(4)
                .build();
    }

}
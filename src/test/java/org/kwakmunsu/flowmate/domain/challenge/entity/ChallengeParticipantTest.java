package org.kwakmunsu.flowmate.domain.challenge.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kwakmunsu.flowmate.global.util.TimeConverter.stringToDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeRole;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.SocialType;

class ChallengeParticipantTest {

    @DisplayName("챌린지 참여자를 생성한다")
    @Test
    void create() {
        Member member = Member.createMember("kwak", "iii148389@naver.com", "12345678", SocialType.KAKAO,
                "https://example.com/profile.jpg");
        ChallengeCreateDomainRequest request = getChallengeCreateDomainRequest();

        Challenge challenge = Challenge.create(request);
        ChallengeParticipant participant = ChallengeParticipant.create(member, challenge, ChallengeRole.LEADER);

        assertThat(participant).isNotNull();
        assertThat(participant.getRole()).isEqualTo(ChallengeRole.LEADER);
    }

    private ChallengeCreateDomainRequest getChallengeCreateDomainRequest() {
        return ChallengeCreateDomainRequest.builder()
                .title("Test Challenge")
                .introduction("Test Introduction")
                .category(InterestCategory.valueOf("DIET"))
                .startDate(stringToDate("2023-01-01"))
                .endDate(stringToDate("2023-01-31"))
                .rule("Test Rule")
                .maxParticipants(4L)
                .build();
    }

}
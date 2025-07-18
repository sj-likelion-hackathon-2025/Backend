package org.kwakmunsu.flowmate.domain.challenge.repository.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeFixture;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeParticipant;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeRole;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeReadDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant.ChallengeParticipantRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChallengeQueryDslRepositoryTest {

    @Autowired
    private ChallengeQueryDslRepository challengeQueryDslRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChallengeParticipantRepository challengeParticipantRepository;

    @DisplayName("챌린지 목록을 조회한다")
    @Test
    void getChallenges() {
        // given
        Member member1 = createAndSaveMember();

        Challenge challenge1 = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge1);
        Challenge challenge2 = ChallengeFixture.createChallenge();
        challengeRepository.save(challenge2);

        // challenge1에 참여자 추가
        ChallengeParticipant participant1 = ChallengeParticipant.create(member1, challenge1, ChallengeRole.LEADER);
        ChallengeParticipant participant2 = ChallengeParticipant.create(member1, challenge2, ChallengeRole.LEADER);
        challengeParticipantRepository.save(participant1);
        challengeParticipantRepository.save(participant2);

        ChallengeReadDomainRequest request = ChallengeFixture.createChallengeReadServiceRequest(1L)
                .toDomainRequest();

        ChallengeListResponse response = challengeQueryDslRepository.findAll(request);

        assertThat(response.challengePreviewResponses()).hasSize(3)
                .extracting("title", "introduction", "maxParticipantCount", "currentParticipantCount", "startDate"
                        , "endDate", "isParticipated")
                .containsExactlyInAnyOrder(
                        tuple("Test Title", "Test Introduction", 4L, 1L, "2023-01-01", "2023-01-31", false),
                        tuple("Test Title", "Test Introduction", 4L, 1L, "2023-01-01", "2023-01-31", false),
                        tuple("일주일 동안 매일 3km 런닝 챌린지", "매일 운동하고 건강해지는 30일 챌린지", 5L, 1L, "2024-07-01", "2024-07-30", true)
                );

        assertThat(response.hasNext()).isFalse();
    }

    private Member createAndSaveMember() {
        Member member = MemberFixture.createMember();
        return memberRepository.save(member);
    }

}
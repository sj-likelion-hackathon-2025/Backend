package org.kwakmunsu.flowmate.domain.challenge.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeFixture;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant.ChallengeParticipantRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChallengeQueryServiceTest {

    @Autowired
    private ChallengeQueryService challengeQueryService;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChallengeParticipantRepository challengeParticipantRepository;

    @DisplayName("챌린지 목록 조회 - 커서 기반 페이징")
    @Test
    void readAll_WithCursorPaging() {
        Member member = createAndSaveMember();

        // 25개 챌린지 생성 (PAGE_SIZE = 20)
        for (int i = 1; i <= 25; i++) {
            Challenge challenge = ChallengeFixture.createChallenge("챌린지" + i);
            challengeRepository.save(challenge);
        }
        // when - 첫 번째 페이지 조회
        ChallengeListResponse firstPageResponse = challengeQueryService.readAll(ChallengeFixture.createChallengeReadServiceRequest(member.getId()));

        // then - 첫 번째 페이지 검증
        assertThat(firstPageResponse.challengePreviewResponses()).hasSize(20);
        assertThat(firstPageResponse.hasNext()).isTrue();
        assertThat(firstPageResponse.nextCursorId()).isNotNull();

        // when - 두 번째 페이지 조회
        ChallengeReadServiceRequest secondPageRequest = ChallengeReadServiceRequest.builder()
                .memberId(member.getId())
                .lastChallengeId(firstPageResponse.nextCursorId())
                .build();

        ChallengeListResponse secondPageResponse = challengeQueryService.readAll(secondPageRequest);

        // then - 두 번째 페이지 검증
        assertThat(secondPageResponse.challengePreviewResponses()).hasSize(5);
        assertThat(secondPageResponse.hasNext()).isFalse();
        assertThat(secondPageResponse.nextCursorId()).isNull();
    }

    private Member createAndSaveMember() {
        Member member = MemberFixture.createMember(1L);
        return memberRepository.save(member);
    }

}
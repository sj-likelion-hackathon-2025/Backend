package org.kwakmunsu.flowmate.domain.challenge.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeFixture;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.ChallengeApplicationRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication.ChallengeApplicationServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.global.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChallengeQueryServiceTest {

    @Autowired
    ChallengeQueryService challengeQueryService;

    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ChallengeApplicationRepository challengeApplicationRepository;

    @Autowired
    ChallengeCommandService challengeCommandService;

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
        ChallengeListResponse firstPageResponse = challengeQueryService.readAll(
                ChallengeFixture.createChallengeReadServiceRequest(member.getId()));

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

    @DisplayName("챌린지 신청자 목록을 조회한다")
    @Test
    void getChallengeApplyList() {
        ChallengeCreateServiceRequest challengeCreateServiceRequest = ChallengeFixture.createChallengeCreateServiceRequest();
        Long challengeId = challengeCommandService.create(challengeCreateServiceRequest);

        ChallengeApplicationServiceRequest challengeApplicationServiceRequest = ChallengeFixture.createChallengeApplyServiceRequest(
                challengeId, 1L, "이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅" );
        challengeCommandService.apply(challengeApplicationServiceRequest);

        ChallengeApplicationListResponse response = challengeQueryService.readApplyList(challengeId, 1L);

        assertThat(response).isNotNull();
        assertThat(response.responses().getFirst().message()).isEqualTo("이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅");
    }

    @DisplayName("리더가 아닐 시챌린지 신청자 목록을 조회하지 못한다")
    @Test
    void failGetChallengeApplyList() {
        Member member = MemberFixture.createMember();
        memberRepository.save(member);

        ChallengeCreateServiceRequest challengeCreateServiceRequest = ChallengeFixture.createChallengeCreateServiceRequest(member.getId());
        Long challengeId = challengeCommandService.create(challengeCreateServiceRequest);

        ChallengeApplicationServiceRequest challengeApplicationServiceRequest = ChallengeFixture.createChallengeApplyServiceRequest(
                challengeId, 1L, "이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅" );
        challengeCommandService.apply(challengeApplicationServiceRequest);

        Assertions.assertThatThrownBy(() -> challengeQueryService.readApplyList(challengeId, 1L))
            .isInstanceOf(UnAuthenticationException.class);
    }

    private Member createAndSaveMember() {
        Member member = MemberFixture.createMember(1L);
        return memberRepository.save(member);
    }

}
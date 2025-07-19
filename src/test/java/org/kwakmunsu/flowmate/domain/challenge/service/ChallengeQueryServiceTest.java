package org.kwakmunsu.flowmate.domain.challenge.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeFixture;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengePreviewResponse;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.ChallengeApplicationRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationListResponse;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplicationrepository.dto.ChallengeApplicationResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.kwakmunsu.flowmate.domain.member.entity.Grade;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.global.exception.UnAuthenticationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChallengeQueryServiceTest {

    @InjectMocks
    ChallengeQueryService challengeQueryService;

    @Mock
    ChallengeRepository challengeRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ChallengeApplicationRepository challengeApplicationRepository;

    @Mock
    ChallengeCommandService challengeCommandService;


    @DisplayName("챌린지 목록 조회 - 커서 기반 페이징")
    @Test
    void readAll_WithCursorPaging() {
        // Given
        Member member = MemberFixture.createMember(1L);

        // 첫 번째 페이지용 Mock 데이터 (20개)
        List<ChallengePreviewResponse> firstPageChallenges = createMockChallenges(1, 20);
        ChallengeListResponse firstPageMockResponse = ChallengeListResponse.builder()
                .challengePreviewResponses(firstPageChallenges)
                .hasNext(true)
                .nextCursorId(20L)
                .build();

        // 두 번째 페이지용 Mock 데이터 (5개)
        List<ChallengePreviewResponse> secondPageChallenges = createMockChallenges(21, 25);
        ChallengeListResponse secondPageMockResponse = ChallengeListResponse.builder()
                .challengePreviewResponses(secondPageChallenges)
                .hasNext(false)
                .nextCursorId(null)
                .build();

        // Repository Mock 설정
        ChallengeReadServiceRequest firstRequest = ChallengeFixture.createChallengeReadServiceRequest(member.getId());
        given(challengeQueryService.readAll(firstRequest)).willReturn(firstPageMockResponse);

        ChallengeReadServiceRequest secondRequest = ChallengeReadServiceRequest.builder()
                .memberId(member.getId())
                .lastChallengeId(20L)
                .build();
        given(challengeQueryService.readAll(secondRequest)).willReturn(secondPageMockResponse);

        // When - 첫 번째 페이지 조회
        ChallengeListResponse firstPageResponse = challengeQueryService.readAll(firstRequest);

        // Then - 첫 번째 페이지 검증
        assertThat(firstPageResponse.challengePreviewResponses()).hasSize(20);
        assertThat(firstPageResponse.hasNext()).isTrue();
        assertThat(firstPageResponse.nextCursorId()).isEqualTo(20L);

        // When - 두 번째 페이지 조회
        ChallengeListResponse secondPageResponse = challengeQueryService.readAll(secondRequest);

        // Then - 두 번째 페이지 검증
        assertThat(secondPageResponse.challengePreviewResponses()).hasSize(5);
        assertThat(secondPageResponse.hasNext()).isFalse();
        assertThat(secondPageResponse.nextCursorId()).isNull();
    }

    @DisplayName("챌린지 신청자 목록을 조회한다")
    @Test
    void getChallengeApplyList() {
        Long challengeId = 1L;
        Long leaderId = 1L;
        List<ChallengeApplicationResponse> applicationResponse = List.of(ChallengeApplicationResponse.builder()
                .memberId(2L)
                .name("신청자1")
                .grade(Grade.ROOKIE)
                .message("이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅")
                .build());
        ChallengeApplicationListResponse expectedResponse = new ChallengeApplicationListResponse(applicationResponse);
        given(challengeApplicationRepository.findByChallengeIdAndStatus(any(Long.class), any(ApprovalStatus.class)))
                .willReturn(expectedResponse);
        given(challengeRepository.existsByIdAndLeaderId(any(Long.class), any(Long.class))).willReturn(true);

        ChallengeApplicationListResponse response = challengeQueryService.readApplyList(challengeId, leaderId);

        assertThat(response).isNotNull();
        assertThat(response.responses()).hasSize(1);
        assertThat(response.responses().getFirst().message()).isEqualTo("이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅");

        verify(challengeApplicationRepository).findByChallengeIdAndStatus(any(Long.class), any(ApprovalStatus.class));
    }

    @DisplayName("리더가 아닐 시챌린지 신청자 목록을 조회하지 못한다")
    @Test
    void failGetChallengeApplyList() {
        // Given
        Long challengeId = 1L;
        given(challengeRepository.existsByIdAndLeaderId(any(Long.class), any(Long.class))).willReturn(false);

        Assertions.assertThatThrownBy(() -> challengeQueryService.readApplyList(challengeId, 1L))
                .isInstanceOf(UnAuthenticationException.class);
    }

    private List<ChallengePreviewResponse> createMockChallenges(int startId, int endId) {
        List<ChallengePreviewResponse> challenges = new ArrayList<>();
        for (int i = startId; i <= endId; i++) {
            ChallengePreviewResponse challenge = ChallengePreviewResponse.builder()
                    .challengeId((long) i)
                    .title("챌린지" + i)
                    .introduction("챌린지 소개" + i)
                    .maxParticipantCount(5L)
                    .currentParticipantCount(1L)
                    .startDate("2024-01-01")
                    .endDate("2024-12-31")
                    .isParticipated(false)
                    .build();
            challenges.add(challenge);
        }
        return challenges;
    }

}
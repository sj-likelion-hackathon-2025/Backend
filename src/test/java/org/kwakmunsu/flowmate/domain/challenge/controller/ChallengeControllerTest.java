package org.kwakmunsu.flowmate.domain.challenge.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeApplyRequest;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeCreateRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.ChallengeCommandService;
import org.kwakmunsu.flowmate.domain.challenge.service.ChallengeQueryService;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengePreviewResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.kwakmunsu.flowmate.security.TestMember;
import org.kwakmunsu.flowmate.security.TestSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    ChallengeCommandService challengeCommandService;

    @MockitoBean
    ChallengeQueryService challengeQueryService;

    @TestMember
    @DisplayName("챌린지 생성에 성공한다")
    @Test
    void create() throws Exception {
        ChallengeCreateRequest request = getChallengeCreateRequest("DIET");

        mockMvc.perform(
                        post("/challenges")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(challengeCommandService).create(request.toServiceRequest(1L));
    }

    @DisplayName("챌린지 생성에 실패한다")
    @Test
    void failedCreate() throws Exception {
        ChallengeCreateRequest request = getChallengeCreateRequest("invalid");

        mockMvc.perform(
                        post("/challenges")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(challengeCommandService, never()).create(request.toServiceRequest(1L));
    }

    @TestMember
    @DisplayName("챌린지 신청을 한다")
    @Test
    void apply() throws Exception {
        ChallengeApplyRequest request = new ChallengeApplyRequest("파이팅 나는 성공할거야 아자아자 파이팅!!!!!");
        Long challengeId = 1L;

        mockMvc.perform(
                        post("/challenges/" + "{challengeId}/apply", challengeId )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @TestMember
    @DisplayName("메세지가 20글자 이하여서 신청에 실패한다")
    @Test
    void failedApply() throws Exception {
        ChallengeApplyRequest request = new ChallengeApplyRequest("invaild");
        Long challengeId = 1L;

        mockMvc.perform(
                        post("/challenges/" + "{challengeId}/apply", challengeId )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @TestMember
    @DisplayName("챌린지 기본 목록 조회")
    @Test
    void getChallenges() throws Exception {
        ChallengeListResponse mockResponse = createMockResponseWithPaging();
        given(challengeQueryService.readAll(any(ChallengeReadServiceRequest.class)))
                .willReturn(mockResponse);
        mockMvc.perform(
                        get("/challenges")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.nextCursorId").value(5));
    }

    private ChallengeListResponse createMockResponseWithPaging() {
        List<ChallengePreviewResponse> challenges = List.of(
                new ChallengePreviewResponse(
                        1L,
                        1L,
                        "아침 6시 기상 챌린지",
                        "매일 아침 6시에 일어나 인증하는 챌린지입니다.",
                        10L,
                        5L,
                        "2024-07-01",
                        "2024-07-31",
                        true
                ),
                new ChallengePreviewResponse(
                        5L,
                        5L,
                        "매일 물 2L 마시기",
                        "하루 2L 이상 물을 마시고 인증하는 건강 챌린지입니다.",
                        30L,
                        0L,
                        "2024-07-20",
                        "2024-08-20",
                        false
                )
        );

        return ChallengeListResponse.builder()
                .challengePreviewResponses(challenges)
                .hasNext(true)
                .nextCursorId(5L)
                .build();
    }

    private ChallengeCreateRequest getChallengeCreateRequest(String category) {
        return ChallengeCreateRequest.builder()
                .title("Test Challenge")
                .introduction("Test")
                .category(category)
                .startDate("2023-01-01")
                .endDate("2023-01-31")
                .rule("Test Rule")
                .maxParticipants(4L)
                .build();
    }

}
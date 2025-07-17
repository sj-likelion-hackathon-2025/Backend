package org.kwakmunsu.flowmate.domain.challenge.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeCreateRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.ChallengeCommandService;
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

    private ChallengeCreateRequest getChallengeCreateRequest(String category) {
        return ChallengeCreateRequest.builder()
                .title("Test Challenge")
                .introduction("Test")
                .category(category)
                .startDate("2023-01-01")
                .endDate("2023-01-31")
                .rule("Test Rule")
                .maxParticipants(4)
                .build();
    }

}
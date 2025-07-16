package org.kwakmunsu.flowmate.domain.member.controller;

import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.BAD_REQUEST;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberCategoryRegisterRequest;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.domain.member.service.MemberCommandService;
import org.kwakmunsu.flowmate.domain.member.service.MemberQueryService;
import org.kwakmunsu.flowmate.global.exception.BadRequestException;
import org.kwakmunsu.flowmate.security.TestMember;
import org.kwakmunsu.flowmate.security.TestSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MemberCommandService memberCommandService;

    @MockitoBean
    MemberQueryService memberQueryService;

    @TestMember
    @DisplayName("회원 프로필 업데이트 테스트")
    @Test
    void updateProfile() throws Exception {
        MemberProfileRequest request = new MemberProfileRequest("kwakmunsu");
        MockMultipartFile profileImage = new MockMultipartFile(
                "image",
                "profile.jpg",
                "image/jpeg",
                "profile image content".getBytes()
        );
        MockMultipartFile requestPart = new MockMultipartFile(
                "request",
                "request.json",
                "application/json",
                objectMapper.writeValueAsString(request).getBytes()
        );

        mockMvc.perform(
                        multipart("/members")
                                .file(requestPart)
                                .file(profileImage)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @TestMember
    @DisplayName("회원 프로필 업데이트 실패 테스트")
    @Test
    void failedUpdateProfile() throws Exception {
        MemberProfileRequest request = new MemberProfileRequest(""); // 빈 닉네임으로 요청
        MockMultipartFile requestPart = new MockMultipartFile(
                "request",
                "request.json",
                "application/json",
                objectMapper.writeValueAsString(request).getBytes()
        );

        mockMvc.perform(
                        multipart("/members")
                                .file(requestPart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 닉네임 중복 확인 API")
    @Test
    void checkDuplicationName() throws Exception {
        String nickname = "kwakmunsu";

        mockMvc.perform(get("/members/check-name")
                        .param("name", nickname)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @TestMember
    @DisplayName("회원 닉네임 중복 API 요청 시 잘못된 파라미터 요청")
    @Test
    void failCheckDuplicationName() throws Exception {
        String nickname = "";
        doThrow(new BadRequestException(BAD_REQUEST))
                .when(memberQueryService).checkDuplicateName(nickname);

        mockMvc.perform(get("/members/check-name")
                        .param("name", nickname)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @TestMember
    @DisplayName("회원 관심 카테고리 등록 기능")
    @Test
    void registerCategory() throws Exception {
        List<String> categories = List.of(
                InterestCategory.EXERCISE.name(),
                InterestCategory.DIET.name(),
                InterestCategory.FINANCE.name()
        );
        MemberCategoryRegisterRequest request = new MemberCategoryRegisterRequest(categories);

        mockMvc.perform(
                        post("/members/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @TestMember
    @DisplayName("회원 관심 카테고리 개수 3개 미만으로 실패 ")
    @Test
    void failRegisterCategory() throws Exception {
        List<String> categories = List.of(
                InterestCategory.EXERCISE.name(),
                InterestCategory.DIET.name()
        );
        MemberCategoryRegisterRequest request = new MemberCategoryRegisterRequest(categories);

        mockMvc.perform(
                        post("/members/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @TestMember
    @DisplayName("잘못된 카테고리 요청으로 실패 ")
    @Test
    void failInvalidCategoryTypeRegisterCategory() throws Exception {
        List<String> categories = List.of(
                "invalid_category",
                "invalid_category",
                "invalid_category"
        );
        MemberCategoryRegisterRequest request = new MemberCategoryRegisterRequest(categories);

        mockMvc.perform(
                        post("/members/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
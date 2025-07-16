package org.kwakmunsu.flowmate.domain.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileRequest;
import org.kwakmunsu.flowmate.domain.member.service.MemberCommandService;
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

    @TestMember
    @DisplayName("회원 프로필 업데이트 테스트")
    @Test
    void updateProfile() throws Exception {
        // given
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
        // given
        MemberProfileRequest request = new MemberProfileRequest(""); // 빈 닉네임으로 요청
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
//                                .file(profileImage)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
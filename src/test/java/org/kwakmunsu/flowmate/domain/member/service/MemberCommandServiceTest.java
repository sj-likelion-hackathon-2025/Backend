package org.kwakmunsu.flowmate.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.SocialType;
import org.kwakmunsu.flowmate.domain.member.repository.MemberRepository;
import org.kwakmunsu.flowmate.infrastructure.s3.S3Provider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest {

    @Mock
    S3Provider s3Provider;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberCommandService memberCommandService;

    @DisplayName("리프레쉬 토큰 업데이트")
    @Test
    void updateRefreshToken() {
        Member member = Member.createMember("kwak","iii148389@naver.com", "12345678", SocialType.KAKAO, "https://example.com/profile.jpg");
        ReflectionTestUtils.setField(member, "id", 1L);

        given(memberRepository.findById(1L)).willReturn(member);

        memberCommandService.updateRefreshToken(1L, "new-refresh-token");

        assertThat(member.getRefreshToken()).isEqualTo("new-refresh-token");
    }

    @DisplayName("프로필 사진 및 이름 변경")
    @Test
    void updateProfile() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        MemberProfileServiceRequest request1 = new MemberProfileServiceRequest(file, "update", 1L);
        Member member = Member.createMember("kwak","iii148389@naver.com", "12345678", SocialType.KAKAO, "https://example.com/profile.jpg");

        given(memberRepository.findById(request1.memberId())).willReturn(member);
        given(s3Provider.uploadImage(file)).willReturn("https://new-upload-url.com/image.jpg");

        memberCommandService.updateProfile(request1);

        assertThat(member.getName()).isEqualTo(request1.name());
        assertThat(member.getProfileImgUrl()).isEqualTo("https://new-upload-url.com/image.jpg");
    }

    @DisplayName("프로필 사진 기본으로 변경")
    @Test
    void updateBasicProfile() {
        MemberProfileServiceRequest request = new MemberProfileServiceRequest(null, "update", 1L);
        Member member = Member.createMember("kwak","iii148389@naver.com", "12345678", SocialType.KAKAO, "https://example.com/profile.jpg");

        given(memberRepository.findById(request.memberId())).willReturn(member);

        memberCommandService.updateProfile(request);

        assertThat(member.getName()).isEqualTo(request.name());
        assertThat(member.getProfileImgUrl()).isNull();

        verify(s3Provider).deleteImage("https://example.com/profile.jpg");
        verify(s3Provider, never()).uploadImage(any());
    }

    @DisplayName("프로필 사진 기본에서 사진 추가 ")
    @Test
    void updateProfileWhenBasicProfile() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        MemberProfileServiceRequest request = new MemberProfileServiceRequest(file, "update", 1L);
        Member member = Member.createMember("kwak","iii148389@naver.com", "12345678", SocialType.KAKAO, null);

        assertThat(member.getProfileImgUrl()).isNull();

        given(memberRepository.findById(request.memberId())).willReturn(member);
        given(s3Provider.uploadImage(file)).willReturn("https://new-upload-url.com/image.jpg");

        memberCommandService.updateProfile(request);

        assertThat(member.getName()).isEqualTo(request.name());
        assertThat(member.getProfileImgUrl()).isEqualTo("https://new-upload-url.com/image.jpg");
        verify(s3Provider, never()).deleteImage(any());
    }

}
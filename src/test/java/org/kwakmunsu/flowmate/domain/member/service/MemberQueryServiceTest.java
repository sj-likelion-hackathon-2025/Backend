package org.kwakmunsu.flowmate.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.domain.member.service.dto.MemberInfoResponse;
import org.kwakmunsu.flowmate.global.exception.DuplicationException;
import org.kwakmunsu.flowmate.global.exception.NotFoundException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberQueryService memberQueryService;

    @DisplayName("회원 기본 정보를 조회한다")
    @Test
    void getProfile() {
        Member member = MemberFixture.createMember();
        given(memberRepository.findById(1L)).willReturn(member);

        MemberInfoResponse profile = memberQueryService.getProfile(1L);

        assertThat(profile).isNotNull();
        assertThat(profile.profileImageUrl()).isEqualTo(member.getProfileImgUrl());
    }

    @DisplayName("존재하지 않는 회원 조회 시 예외를 던진다.")
    @Test
    void failedGetProfile() {
        given(memberRepository.findById(1L)).willThrow(new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER));

        assertThatThrownBy(() -> memberQueryService.getProfile(1L))
            .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("중복되지 않은 회원 닉네임")
    @Test
    void checkValidName() {
        String nickname = "uniqueNickname";
        given(memberRepository.existsByName(nickname)).willReturn(false);

        memberQueryService.checkDuplicateName(nickname);
    }

    @DisplayName("중복된 회원 닉네임 요청")
    @Test
    void checkInvalidName() {
        String nickname = "duplicationNickname";
        given(memberRepository.existsByName(nickname)).willReturn(true);

        assertThatThrownBy(() -> memberQueryService.checkDuplicateName(nickname))
            .isInstanceOf(DuplicationException.class);
    }

}
package org.kwakmunsu.flowmate.domain.member.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.member.repository.MemberRepository;
import org.kwakmunsu.flowmate.global.exception.DuplicationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberQueryService memberQueryService;

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
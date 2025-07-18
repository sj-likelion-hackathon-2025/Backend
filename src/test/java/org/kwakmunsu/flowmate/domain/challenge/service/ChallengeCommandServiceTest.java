package org.kwakmunsu.flowmate.domain.challenge.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeApply;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeFixture;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeParticipant;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant.ChallengeParticipantRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository.ChallengeApplyRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.ChallengeApplyServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberFixture;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.global.exception.DuplicationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChallengeCommandServiceTest {

    @Mock
    ChallengeRepository challengeRepository;

    @Mock
    ChallengeParticipantRepository challengeParticipantRepository;

    @Mock
    ChallengeApplyRepository challengeApplyRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    ChallengeCommandService challengeCommandService;

    @DisplayName("챌린지 생성에 성공한다")
    @Test
    void create() {
        ChallengeCreateServiceRequest request = ChallengeFixture.createChallengeCreateServiceRequest();

        challengeCommandService.create(request);

        verify(challengeRepository).save(any(Challenge.class));
        verify(memberRepository).findById(request.memberId());
        verify(challengeParticipantRepository).save(any(ChallengeParticipant.class));
    }


    @DisplayName("챌린지 신청에 성공한다")
    @Test
    void apply() {
        Member member = MemberFixture.createMember();
        Challenge challenge = ChallengeFixture.createChallenge(1L);
        ChallengeApplyServiceRequest request = ChallengeFixture.createChallengeApplyServiceRequest(
                "이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅");

        given(memberRepository.findById(any())).willReturn(member);
        given(challengeRepository.findById(any())).willReturn(challenge);

        ChallengeApply.create(member, challenge.getId(), request.message());
        challengeCommandService.apply(request);

        verify(challengeApplyRepository).save(any(ChallengeApply.class));
    }

    @DisplayName("이미 신청한 챌린지 신청을 할 경우 예외를 던진다.")
    @Test
    void failedApplyWhenAlreadyApply() {
        Member member = MemberFixture.createMember();
        Challenge challenge = ChallengeFixture.createChallenge(1L);
        ChallengeApplyServiceRequest request = ChallengeFixture.createChallengeApplyServiceRequest(
                "이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅");

        given(memberRepository.findById(any())).willReturn(member);
        given(challengeRepository.findById(any())).willReturn(challenge);
        given(challengeApplyRepository.existsMemberIdAndChallengeId(any(), any())).willReturn(true);

        assertThatThrownBy(() -> challengeCommandService.apply(request))
                .isInstanceOf(DuplicationException.class);
    }

    @DisplayName("신청 인원이 초과하여 예외를 던진다.")
    @Test
    void failedApplyWhenOverCapacity() {
        Member member = MemberFixture.createMember();
        Challenge challenge = ChallengeFixture.createChallenge(1L); // 4명
        ChallengeApplyServiceRequest request = ChallengeFixture.createChallengeApplyServiceRequest(
                "이번엔 꼭 성공하겠습니다. 파이팅 파이팅 파이팅");

        given(memberRepository.findById(any())).willReturn(member);
        given(challengeRepository.findById(any())).willReturn(challenge);
        int maxCount = (int) (challenge.getMaxParticipants() * 2);
        given(challengeApplyRepository.countApplicantsWithLimit(any(Long.class), any(Integer.class))).willReturn(maxCount);

        assertThatThrownBy(() -> challengeCommandService.apply(request))
                .isInstanceOf(DuplicationException.class);
    }

}
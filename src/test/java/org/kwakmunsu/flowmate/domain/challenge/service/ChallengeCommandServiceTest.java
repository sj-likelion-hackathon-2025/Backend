package org.kwakmunsu.flowmate.domain.challenge.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeParticipant;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant.ChallengeParticipantRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
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
    MemberRepository memberRepository;

    @InjectMocks
    ChallengeCommandService challengeCommandService;

    @DisplayName("챌린지 생성에 성공한다")
    @Test
    void create() {
        ChallengeCreateServiceRequest request = getChallengeCreateServiceRequest();

        challengeCommandService.create(request);

        verify(challengeRepository).save(any(Challenge.class));
        verify(memberRepository).findById(request.memberId());
        verify(challengeParticipantRepository).save(any(ChallengeParticipant.class));
    }

    private ChallengeCreateServiceRequest getChallengeCreateServiceRequest() {
        return ChallengeCreateServiceRequest.builder()
                .title("Test Challenge")
                .introduction("Test Introduction")
                .category("DIET")
                .startDate("2023-01-01")
                .endDate("2023-01-31")
                .rule("Test Rule")
                .maxParticipants(4)
                .memberId(1L)
                .build();
    }

}
package org.kwakmunsu.flowmate.domain.challenge.repository.challengeParticipant;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeParticipant;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeParticipantRepository {

    private final ChallengeParticipantJpaRepository challengeParticipantJpaRepository;

    public void save(ChallengeParticipant challengeParticipant) {
        challengeParticipantJpaRepository.save(challengeParticipant);
    }

}
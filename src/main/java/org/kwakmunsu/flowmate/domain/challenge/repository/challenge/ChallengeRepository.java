package org.kwakmunsu.flowmate.domain.challenge.repository.challenge;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeRepository {

    private final ChallengeJpaRepository challengeJpaRepository;

    public void save(Challenge challenge) {
        challengeJpaRepository.save(challenge);
    }

}
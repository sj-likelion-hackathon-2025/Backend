package org.kwakmunsu.flowmate.domain.challenge.repository.challenge;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeReadDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeRepository {

    private final ChallengeJpaRepository challengeJpaRepository;
    private final ChallengeQueryDslRepository challengeQueryDslRepository;

    public void save(Challenge challenge) {
        challengeJpaRepository.save(challenge);
    }

    public ChallengeListResponse findAll(ChallengeReadDomainRequest request) {
        return challengeQueryDslRepository.findAll(request);
    }

}
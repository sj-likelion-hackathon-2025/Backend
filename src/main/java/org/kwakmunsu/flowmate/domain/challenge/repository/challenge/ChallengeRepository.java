package org.kwakmunsu.flowmate.domain.challenge.repository.challenge;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeReadDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeListResponse;
import org.kwakmunsu.flowmate.global.exception.NotFoundException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
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

    public boolean existsByIdAndLeaderId(Long challengeId, Long leaderId) {
        return challengeJpaRepository.existsByIdAndLeaderId(challengeId, leaderId);
    }

    public Challenge findById(Long challengeId) {
        return challengeJpaRepository.findById(challengeId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_CHALLENGE));
    }

}
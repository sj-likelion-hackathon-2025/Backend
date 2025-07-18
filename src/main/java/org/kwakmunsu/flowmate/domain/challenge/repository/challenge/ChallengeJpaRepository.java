package org.kwakmunsu.flowmate.domain.challenge.repository.challenge;

import org.kwakmunsu.flowmate.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeJpaRepository extends JpaRepository<Challenge, Long> {

    boolean existsByIdAndLeaderId(Long challengeId, Long leaderId);

}
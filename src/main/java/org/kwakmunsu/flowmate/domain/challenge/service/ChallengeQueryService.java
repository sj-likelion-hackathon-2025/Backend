package org.kwakmunsu.flowmate.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.ChallengeRepository;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeQueryService {

    private final ChallengeRepository challengeRepository;

    public ChallengeListResponse readAll(ChallengeReadServiceRequest request) {
        return challengeRepository.findAll(request.toDomainRequest());
    }

}
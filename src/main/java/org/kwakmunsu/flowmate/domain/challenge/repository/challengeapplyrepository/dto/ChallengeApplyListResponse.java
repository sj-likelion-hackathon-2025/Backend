package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "챌린지 신청자 목록")
public record ChallengeApplyListResponse(
        @Schema(description = "챌린지 신청자 정보 목록")
        List<ChallengeApplyResponse> responses
) {

}
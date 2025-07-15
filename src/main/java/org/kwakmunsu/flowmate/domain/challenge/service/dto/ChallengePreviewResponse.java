package org.kwakmunsu.flowmate.domain.challenge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChallengePreviewResponse(
        @Schema(description = "챌린지 ID", example = "1")
        Long challengeId,

        @Schema(description = "챌린지 제목", example = "아침 6시 기상 챌린지")
        String title,

        @Schema(description = "챌린지 소개", example = "매일 아침 6시에 일어나 인증하는 챌린지입니다.")
        String introduction,

        @Schema(description = "최대 참가자 수", example = "10")
        Integer maxParticipantCount,

        @Schema(description = "현재 참가자 수", example = "5")
        Integer currentParticipantCount,

        @Schema(description = "챌린지 시작일", example = "2024-07-01")
        String startDate,

        @Schema(description = "챌린지 종료일", example = "2024-07-31")
        String endDate,

        @Schema(description = "참여 여부", example = "true")
        boolean isParticipated
) {

}
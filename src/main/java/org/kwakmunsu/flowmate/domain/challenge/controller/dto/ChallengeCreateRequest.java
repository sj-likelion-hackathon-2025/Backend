package org.kwakmunsu.flowmate.domain.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "새 챌린지 생성 요청")
public record ChallengeCreateRequest(
        @Schema(description = "챌린지 제목", example = "일주일 동안 매일 3km 런닝 챌린지")
        String title,

        @Schema(description = "챌린지 한줄 소개", example = "매일 운동하고 건강해지는 30일 챌린지")
        String Introduction,

        @Schema(description = "챌린지 카테고리", example = "EXERCISE")
        String category,

        @Schema(description = "시작일 (yyyy-MM-dd)", example = "2024-07-01")
        String startDate,

        @Schema(description = "종료일 (yyyy-MM-dd)", example = "2024-07-30")
        String endDate,

        @Schema(description = "인증 규칙", example = "매일 3km 이상 달리는 걸 시간 포함해서 인증샷을 올려주세요")
        String roles,

        @Schema(description = "최대 참가자 수", example = "5")
        Integer maxParticipants
) {
}
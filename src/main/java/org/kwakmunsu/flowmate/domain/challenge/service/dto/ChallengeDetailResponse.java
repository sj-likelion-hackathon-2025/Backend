package org.kwakmunsu.flowmate.domain.challenge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "챌린지 상세 조회 응답 DTO")
public record ChallengeDetailResponse(
        @Schema(description = "챌린지 ID", example = "1")
        Long challengeId,

        @Schema(description = "챌린지 제목", example = "건강한 식습관 챌린지")
        String title,

        @Schema(description = "챌린지 규칙", example = "매일 아침 7시에 건강한 아침식사를 먹고 시각과 음식을 보이도록 촬영 후 업로드")
        String roles,

        @Schema(description = "챌린지 시작 날짜", example = "2025-10-01")
        String startDate,

        @Schema(description = "챌린지 종료 날짜", example = "2025-10-21")
        String endDate,

        @Schema(description = "챌린지 참여자 리스트")
        List<ChallengerPreviewResponse> responses
) {

}
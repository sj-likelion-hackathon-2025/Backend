package org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "챌린지 인증 결과 응답")
public record ChallengeCertificationPreviewResponse(
        @Schema(description = "챌린저 이름", example = "kwakmunsu")
        String name,

        @Schema(description = "챌린저 등급", example = "ROOKIE")
        String grade,

        @Schema(description = "챌린지 시작 기간", example = "2023-01-01")
        String startDate,

        @Schema(description = "챌린지 마감 기간", example = "2023-01-07")
        String endDate,

        @Schema(description = "챌린지 인증 결과 목록")
        List<ChallengeCertificationResultResponse> ChallengeCertificationResultResponses
) {

}
package org.kwakmunsu.flowmate.domain.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챌린지 신청 요청")
public record ChallengeApplyRequest(
        @Schema(description = "챌린지 신청 각오 ", example = "전 여친한테 복수하고 싶어요 살 꼭 뺄 겁니다!!")
        String message
) {

}
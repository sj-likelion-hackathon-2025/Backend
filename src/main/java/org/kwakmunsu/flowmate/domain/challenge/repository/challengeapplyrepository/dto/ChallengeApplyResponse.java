package org.kwakmunsu.flowmate.domain.challenge.repository.challengeapplyrepository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.kwakmunsu.flowmate.domain.member.entity.Grade;

@Builder
public record ChallengeApplyResponse(
        @Schema(description = "신청자 id", example = "1")
        Long memberId,

        @Schema(description = "신청자 이름", example = "곽태풍")
        String name,

        @Schema(description = "신청자 등급", example = "ROOKIE")
        Grade grade,

        @Schema(description = "신청자 각오 메세지", example = "전여친한테 복수하겠습니다. 이 챌린지가 저에게 큰 동기부여가 됩니다.")
        String message
) {

}
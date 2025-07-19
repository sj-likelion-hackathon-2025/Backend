package org.kwakmunsu.flowmate.domain.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengeApplication.ChallengeApplicationServiceRequest;

@Schema(description = "챌린지 신청 요청")
public record ChallengeApplicationRequest(
        @Schema(description = "챌린지 신청 각오 - 최소 20글자 이상 입력해야합니다.", example = "전 여친한테 복수하고 싶어요 살 꼭 뺄 겁니다!!")
        @NotBlank(message = "신청 각오를 입력해주세요.")
        @Size(min = 20, message = "20 글자 이상 입력해주세요.")
        String message
) {

    public ChallengeApplicationServiceRequest toServiceRequest(Long challengeId, Long memberId) {
        return ChallengeApplicationServiceRequest.builder()
                .message(message)
                .challengeId(challengeId)
                .memberId(memberId)
                .build();
    }

}
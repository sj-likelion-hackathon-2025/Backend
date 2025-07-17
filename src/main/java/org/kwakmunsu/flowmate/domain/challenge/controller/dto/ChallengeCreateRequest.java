package org.kwakmunsu.flowmate.domain.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.global.annotation.EnumValid;

@Schema(description = "새 챌린지 생성 요청")
@Builder
public record ChallengeCreateRequest(
        @Schema(description = "챌린지 제목", example = "일주일 동안 매일 3km 런닝 챌린지")
        @NotBlank(message = "챌린지 제목은 필수입니다.")
        String title,

        @Schema(description = "챌린지 한줄 소개 - 최대 30자까지 가능합니다.", example = "매일 운동하고 건강해지는 30일 챌린지")
        @NotBlank(message = "챌린지 한줄 소개는 필수입니다.")
        @Size(max = 30, message = "챌린지 한줄 소개는 최대 30자까지 입력 가능합니다.")
        String introduction,

        @Schema(description = "챌린지 카테고리", example = "EXERCISE")
        @EnumValid(enumClass = InterestCategory.class, message = "유효하지 않은 카테고리입니다.")
        String category,

        @Schema(description = "시작일 (yyyy-MM-dd)", example = "2024-07-01")
        @NotBlank(message = "시작일은 필수입니다.")
        String startDate,

        @Schema(description = "종료일 (yyyy-MM-dd)", example = "2024-07-30")
        @NotBlank(message = "종료일은 필수입니다.")
        String endDate,

        @Schema(description = "인증 규칙", example = "매일 3km 이상 달리는 걸 시간 포함해서 인증샷을 올려주세요")
        @NotBlank(message = "인증 규칙은 필수입니다.")
        String rule,

        @Schema(description = "최대 참가자 수 - 5명까지 가능합니다.", example = "5")
        @Min(value = 2, message = "최소 참여자 수는 2명 이상이어야 합니다")
        @Max(value = 5, message = "최대 참여자 수는 5명 이하여야 합니다")
        Integer maxParticipants
) {

    public ChallengeCreateServiceRequest toServiceRequest(Long memberId) {
        return ChallengeCreateServiceRequest.builder()
                .title(title)
                .introduction(introduction)
                .category(category)
                .startDate(startDate)
                .endDate(endDate)
                .rule(rule)
                .maxParticipants(maxParticipants)
                .memberId(memberId)
                .build();
    }

}
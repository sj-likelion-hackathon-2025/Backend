package org.kwakmunsu.flowmate.domain.member.controller.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.domain.member.service.dto.MemberCategoryRegisterServiceRequest;
import org.kwakmunsu.flowmate.global.exception.BadRequestException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;

@Schema(description = "회원 카테고리 등록 요청 DTO")
public record MemberCategoryRegisterRequest(
        @Schema(
                description = "회원 카테고리 목록 - 최소 3가지 이상.",
                example = "[\"EXERCISE\", \"STUDY\", \"DIET\", \"LIFESTYLE\", \"FINANCE\", \"PROJECT\"]",
                requiredMode = REQUIRED
        )
        @Size(min = 3, message = "카테고리는 최소 3개 이상 선택해야 합니다")
        List<String> categories
) {

    public MemberCategoryRegisterServiceRequest toServiceRequest(Long memberId) {
        try {
            List<InterestCategory> interestCategories = categories.stream()
                    .map(InterestCategory::valueOf)
                    .toList();

            return MemberCategoryRegisterServiceRequest.builder()
                    .memberId(memberId)
                    .categories(interestCategories)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorStatus.INVALID_CATEGORY);
        }

    }

}
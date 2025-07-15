package org.kwakmunsu.flowmate.domain.member.controller.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "회원 카테고리 등록 요청 DTO")
public record MemberCategoryRegisterRequest(
        @Schema(
                description = "회원 카테고리 목록 - 최소 3가지 이상.",
                example = "[\"EXERCISE\", \"STUDY\", \"DIET\", \"LIFESTYLE\", \"FINANCE\", \"PROJECT\"]",
                requiredMode = REQUIRED
        )
        List<String> categories
) {

}
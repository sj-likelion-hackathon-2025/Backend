package org.kwakmunsu.flowmate.domain.member.controller;

import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.BAD_REQUEST;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.DUPLICATE_NICKNAME;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.INTERNAL_SERVER_ERROR;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.NOT_FOUND_MEMBER;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.UNAUTHORIZED_ERROR;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberCategoryRegisterRequest;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileRequest;
import org.kwakmunsu.flowmate.domain.member.service.dto.MemberInfoResponse;
import org.kwakmunsu.flowmate.global.annotation.ApiExceptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member API", description = "회원 관련 API 문서입니다.")
public abstract class MemberDocsController {

    @Operation(
            summary = "프로필 기본 정보 조회 - JWT O",
            description = """
                    프로필 기본 정보(프로필 사진, 네임, 등급, 포인트)를 조회합니다.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로필 정보 조회 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MemberInfoResponse.class)
            )
    )
    @ApiExceptions(values = {
            UNAUTHORIZED_ERROR,
            NOT_FOUND_MEMBER,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<MemberInfoResponse> getProfile(Long memberId);

    @Operation(
            summary = "프로필 정보 등록 요청 - JWT O",
            description = """
                    프로필 사진, 이름 입력해 요청합니다.
                    """
    )
    @RequestBody(
            description = "프로필 정보 등록 요청",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MemberProfileRequest.class)
            )
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> register(MemberProfileRequest request, Long memberId);

    @Operation(
            summary = "닉네임 중복 확인 - JWT O"
    )
    @Parameter(
            name = "name",
            description = "중복 확인할 닉네임",
            in = ParameterIn.QUERY,
            required = true
    )
    @ApiResponse(
            responseCode = "204",
            description = "닉네임 중복 확인 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            DUPLICATE_NICKNAME,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> checkDuplicateName(String name, Long memberId);

    @Operation(
            summary = "회원 관심 카테고리 등록 - JWT O"
    )
    @RequestBody(
            description = "회원 관심 카테고리 등록 요청",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MemberCategoryRegisterRequest.class)
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "회원 관심 카테고리 등록 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> registerCategory(MemberCategoryRegisterRequest request, Long memberId);

}
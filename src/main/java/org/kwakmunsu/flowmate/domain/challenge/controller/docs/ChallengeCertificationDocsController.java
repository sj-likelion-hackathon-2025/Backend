package org.kwakmunsu.flowmate.domain.challenge.controller.docs;

import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.BAD_REQUEST;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.INTERNAL_SERVER_ERROR;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.UNAUTHORIZED_ERROR;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.CertificationStatusRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification.ChallengeCertificationDetailResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification.ChallengeCertificationPreviewResponse;
import org.kwakmunsu.flowmate.global.annotation.ApiExceptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ChallengeCertification API", description = "챌린지 인증 관련 문서입니다.")
public abstract class ChallengeCertificationDocsController {

    @Operation(
            summary = "챌린지 인증 요청 - JWT O",
            description = """
                     ### 챌린지 미션 인증
                     <ul>
                         <li> 챌린지 참여자만 인증 가능합니다.</li>
                         <li> 챌린지 ID와 인증 요청 정보를 포함하여 챌린지를 인증합니다.</li>
                     </ul>
                    """
    )
    @Parameter(
            name = "certificationId",
            description = "챌린지 인증 ID",
            required = true,
            in = ParameterIn.PATH,
            example = "42"
    )
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 인증 요청 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> submit(
            Long certificationId,
            @RequestBody(
                    description = "인증 사진 파일 (이미지)",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            MultipartFile image,
            Long memberId
    );

    //인증 상세 확인
    @Operation(
            summary = "챌린지 인증 상세 조회 요청 - JWT O",
            description = """
                     ### 챌린지 인증 상세 조회 인증
                     <ul>
                         <li> 챌린지 리더와 인증 조회 가능합니다.</li>
                     </ul>
                    """
    )
    @Parameters(value = {
            @Parameter(
                    name = "certificationId",
                    description = "챌린지 인증 ID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "42"
            )
    })
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 인증 상세 조회 요청 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ChallengeCertificationDetailResponse.class)
            )
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<ChallengeCertificationDetailResponse> read(Long certificationId, Long memberId);
    // api/challenge/{certificationId}/{challengerId}certification?date=2025-10-01

    // 인증 승인 절차
    @Operation(summary = "챌린지 인증 승인 요청 - JWT O")

    @Parameters(value = {
            @Parameter(
                    name = "certificationId",
                    description = "챌린지 인증 ID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "42"
            ),
            @Parameter(
                    name = "status",
                    description = "승인 여부",
                    required = true,
                    in = ParameterIn.QUERY,
                    example = "VERIFIED | REJECTED"
            )
    })
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 인증 승인 요청 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> approval(Long certificationId, CertificationStatusRequest request, Long memberId);

    // 사용자 인증 목록 조회
    @Operation(
            summary = "사용자 챌린지 인증 목록 조회 요청 - JWT O",
            description = """
                     ### 사용자 챌린지 인증 목록 조회
                     <ul>
                         <li> 챌린지 기간 인증 결과를 확인합니다.</li>
                     </ul>
                    """
    )
    @Parameters(value = {
            @Parameter(
                    name = "challengeId",
                    description = "챌린지 ID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "42"
            )
    })
    @ApiResponse(
            responseCode = "200",
            description = "사용자 챌린지 인증 목록 조회 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ChallengeCertificationPreviewResponse.class)
            )
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<ChallengeCertificationPreviewResponse> readAllByMember(Long challengeId, Long memberId);

}
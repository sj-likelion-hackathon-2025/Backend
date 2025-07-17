package org.kwakmunsu.flowmate.domain.challenge.controller.docs;

import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.BAD_REQUEST;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.DELETE_UNAUTHORIZED;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.INTERNAL_SERVER_ERROR;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.MODIFY_UNAUTHORIZED;
import static org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus.OVER_CAPACITY_CHALLENGE;
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
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeApplyRequest;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeCreateRequest;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeListType;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.SortBy;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeDetailResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.global.annotation.ApiExceptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "챌린지 API", description = "챌린지 관련 API 문서입니다.")
public abstract class ChallengeDocsController {

    @Operation(
            summary = "챌린지 생성 요청 - JWT O",
            description = "챌린지를 생성합니다. 요청 본문에 챌린지 생성 정보를 포함해야 합니다."
    )
    @RequestBody(
            description = "챌린지 생성 요청 정보",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ChallengeCreateRequest.class)
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 생성 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> create(ChallengeCreateRequest request, Long memberId);

    @Operation(
            summary = "챌린지 신청 - JWT O ",
            description = "챌린지 신청을 합니다. 요청 본문에 챌린지 신청 정보를 포함해야 합니다."
    )
    @RequestBody(
            description = """
                    챌린지 신청 요청 정보
                    <ul>
                        <li>20글자 이상 입력하셔야 합니다.</li>
                    </ul>
                    """,
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ChallengeApplyRequest.class)
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 신청 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            OVER_CAPACITY_CHALLENGE,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> apply(ChallengeApplyRequest request, Long memberId);

    @Operation(
            summary = "챌린지 목록 조회",
            description = """
                    ### 챌린지 목록 조회 API 안내
                    
                    - **검색**
                      - `q` 파라미터로 챌린지 제목을 기준으로 검색할 수 있습니다.
                    - **정렬**
                      - `sortBy` 파라미터로 정렬 방식을 설정할 수 있습니다.
                      - POPULAR: 인기순, NEWEST(기본값): 최신순, OLDEST: 오래된 순
                    - **카테고리 필터**
                      - `interestCategory` 파라미터로 관심 카테고리로 필터링이 가능합니다.
                      - EXERCISE, STUDY, DIET, LIFESTYLE, FINANCE, PROJECT  정확한 값을 입력해주세요.
                    - **조회 타입 선택**
                      - `challengeListType` 파라미터로 목록 유형을 지정할 수 있습니다.
                      - RECRUITING(기본값): 모집중인 챌린지, MINE: 내가 참여한 챌린지
                    - **커서 페이징**
                      - `lastChallengeId` 파라미터로 Cursor 기반 페이징 처리가 가능합니다.
                      - Null 값은 처음부터 조회하며, 이후에는 마지막으로 조회된 챌린지 ID를 입력하여 다음 페이지를 조회합니다.
                    - 원하는 조건 조합으로 다양한 챌린지 목록을 조회할 수 있습니다.<br>
                    - 예시: `?q=운동&sortBy=POPULAR&interestCategory=EXERCISE&challengeListType=RECRUITING&lastChallengeId=10` <br>
                    - 파라미터의 정확한 값을 입력해주세요.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 목록 조회 성공 - 기본 챌린지 목록 일때는 JWT X, 그 외는 JWT O",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ChallengeListResponse.class)
            )
    )
    @Parameters(value = {
            @Parameter(
                    name = "q",
                    description = "검색 키워드 - 챌린지 제목 기준 검색",
                    example = "운동",
                    schema = @Schema(type = "string")
            ),
            @Parameter(
                    name = "sortBy",
                    description = "정렬 조건 - POPULAR, NEWEST, OLDEST",
                    schema = @Schema(implementation = SortBy.class)
            ),
            @Parameter(
                    name = "interestCategory",
                    description = "관심 카테고리 - EXERCISE, STUDY, DIET, LIFESTYLE, FINANCE, PROJECT",
                    schema = @Schema(implementation = InterestCategory.class)
            ),
            @Parameter(
                    name = "challengeListType",
                    description = "챌린지 조회 타입 - RECRUITING: 모집중, MINE: 내 챌린지",
                    schema = @Schema(implementation = ChallengeListType.class, example = "RECRUITING")
            ),
            @Parameter(
                    name = "lastChallengeId",
                    description = "마지막으로 조회된 챌린지 ID (페이지네이션, Cursor 기반)",
                    schema = @Schema(type = "Long", example = "10")
            )
    })
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<ChallengeListResponse> readAll(
            Long memberId,
            String q,
            SortBy sortBy,
            InterestCategory interestCategory,
            ChallengeListType challengeListType,
            Long lastChallengeId
    );

    @Operation(
            summary = "챌린지 상세 조회 - JWT O",
            description = "챌린지 상세 조회를 합니다. 챌린지 참여자만 접근 가능합니다."
    )
    @Parameter(
            name = "challengeId",
            description = "상세조회 챌린지 ID",
            required = true,
            in = ParameterIn.PATH,
            example = "42"
    )
    @ApiResponse(
            responseCode = "200",
            description = "챌린지 상세 조회 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ChallengeDetailResponse.class)
            )
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<ChallengeDetailResponse> read(Long challengeId, Long memberId);

    // 수정
    @Operation(
            summary = "챌린지 정보 수정 - JWT O",
            description = """
                     챌린지 정보 수정을 합니다.
                     <ul>
                         <li>챌린지 제목, 설명, 시작일, 종료일, 최대 인원 수를 수정할 수 있습니다.</li>
                         <li>챌린지의 리더만 접근 가능합니다.</li>
                         <li>챌린지의 상태가 모집중인 경우에만 수정할 수 있습니다.</li>
                     </ul>
                    """
    )
    @Parameter(
            name = "challengeId",
            description = "수정할 챌린지 ID",
            required = true,
            in = ParameterIn.PATH,
            example = "42"
    )
    @ApiResponse(
            responseCode = "204",
            description = "챌린지 수정 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            MODIFY_UNAUTHORIZED,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> update(Long challengeId, Long memberId);

    // 삭제
    @Operation(
            summary = "챌린지 삭제 - JWT O",
            description = """
                     챌린지를 삭제 합니다.
                     <ul>
                         <li>챌린지의 리더만 삭제 가능합니다.</li>
                         <li>챌린지의 상태가 모집중인 경우에만 삭제할 수 있습니다.</li>
                     </ul>
                    """
    )
    @Parameter(
            name = "challengeId",
            description = "삭제할 챌린지 ID",
            required = true,
            in = ParameterIn.PATH,
            example = "42"
    )
    @ApiResponse(
            responseCode = "204",
            description = "챌린지 삭제 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            DELETE_UNAUTHORIZED,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> delete(Long challengeId, Long memberId);

}

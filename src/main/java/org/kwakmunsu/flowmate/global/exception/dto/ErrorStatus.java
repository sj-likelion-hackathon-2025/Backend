package org.kwakmunsu.flowmate.global.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    // <=============== 공통 ===============>
    BAD_REQUEST(400, "ERROR - 잘못된 요청입니다."),

    // <=============== 회원 ===============>
    NOT_FOUND_MEMBER(404, "ERROR - 회원을 찾을 수 없습니다."),
    BAD_REQUEST_MEMBER(400, "ERROR - 잘못된 회원 요청"),
    DUPLICATE_NICKNAME(409, "ERROR - 중복되는 닉네임입니다."),
    MODIFY_UNAUTHORIZED(401, "ERROR - 수정 권한이 없습니다."),
    DELETE_UNAUTHORIZED(401, "ERROR - 삭제 권한이 없습니다."),

    // <=============== 챌린지 ===============>
    NOT_FOUND_CHALLENGE(404, "ERROR - 해당 챌린지를 찾을 수 없습니다."),
    BAD_REQUEST_CHALLENGE(400, "ERROR - 잘못된 챌린지 요청"),
    BAD_REQUEST_APPLY(400, "ERROR - 잘못된 신청입니다."),
    OVER_CAPACITY_CHALLENGE(422, "ERROR - 챌린지 신청 인원이 초과되었습니다."),

    // <=============== JWT ===============>
    INVALID_TOKEN(401,"ERROR - 유효하지 않은 토큰입니다."),
    NOT_FOUND_TOKEN(404,"ERROR - 토큰을 찾을 수 없습니다."),

    //  <=============== AWS ===============>
    AWS_S3_ERROR(500, "ERROR - AWS S3 내부 에러"),
    NOT_FOUND_FILE(404, "ERROR - 파일이 존재하지 않습니다."),
    FAILED_TO_UPLOAD_FILE(500, "ERROR - 파일 업로드에 실패하였습니다."),
    INVALID_FILE_EXTENSION(400, "ERROR - 지원하지 않는 파일 확장자입니다."),

    // <=============== ETC ===============>
    INTERNAL_SERVER_ERROR(500,"ERROR - 서버 내부 에러"),
    UNAUTHORIZED_ERROR(401, "ERROR - 인증되지 않은 사용자입니다."),
    FORBIDDEN_ERROR(403, "ERROR - 접근 권한이 없습니다."),
    BAD_REQUEST_ARGUMENT(400, "ERROR - 유효하지 않은 인자입니다."),
    ;

    private final int statusCode;
    private final String message;

}
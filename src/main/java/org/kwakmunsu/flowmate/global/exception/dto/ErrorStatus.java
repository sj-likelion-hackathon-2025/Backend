package org.kwakmunsu.flowmate.global.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    // <=============== 공통 ===============>
    BAD_REQUEST(400, "ERROR - 잘못된 요청입니다."),

    // <=============== 회원 ===============>
    BAD_REQUEST_MEMBER (400, "ERROR - 잘못된 회원 요청"),
    MODIFY_UNAUTHORIZED(401, "ERROR - 수정 권한이 없습니다."),
    DELETE_UNAUTHORIZED(401, "ERROR - 삭제 권한이 없습니다."),
    NOT_FOUND_MEMBER   (404, "ERROR - 회원을 찾을 수 없습니다."),
    DUPLICATE_NICKNAME (409, "ERROR - 중복되는 닉네임입니다."),

    // <=============== 관심 카테고리 ===============>
    INVALID_CATEGORY(400, "ERROR - 유효하지 않은 카테고리 값이 포함되어 있습니다"),

    // <=============== 챌린지 ===============>
    BAD_REQUEST_CHALLENGE(400, "ERROR - 잘못된 챌린지 요청"),
    INVALID_DATE_FORMAT  (400,"yyyy-MM-dd 형식으로 입력해주세요."),
    NOT_FOUND_CHALLENGE  (404, "ERROR - 해당 챌린지를 찾을 수 없습니다."),

    // <=============== 챌린지 신청 ===============>
    BAD_REQUEST_APPLICATION     (400, "ERROR - 잘못된 신청입니다."),
    NOT_FOUND_APPLICATION       (404, "ERROR - 존재하지 않는 신청 항목입니다."),
    DUPLICATE_APPLICATION       (409, "ERROR - 이미 신청하신 챌린지입니다."),
    ALREADY_DECISION_APPLICATION(409, "ERROR - 해당 신청은 이미 승인 또는 거절 처리되었습니다."),
    OVER_CAPACITY_APPLICATION   (422, "ERROR - 챌린지 신청 인원이 초과되었습니다."),

    // <=============== JWT ===============>
    INVALID_TOKEN  (401,"ERROR - 유효하지 않은 토큰입니다."),
    NOT_FOUND_TOKEN(404,"ERROR - 토큰을 찾을 수 없습니다."),

    //  <=============== AWS ===============>
    INVALID_FILE_EXTENSION(400, "ERROR - 지원하지 않는 파일 확장자입니다."),
    NOT_FOUND_FILE        (404, "ERROR - 파일이 존재하지 않습니다."),
    AWS_S3_ERROR          (500, "ERROR - AWS S3 내부 에러"),
    FAILED_TO_UPLOAD_FILE (500, "ERROR - 파일 업로드에 실패하였습니다."),

    // <=============== ETC ===============>
    BAD_REQUEST_ARGUMENT  (400, "ERROR - 유효하지 않은 인자입니다."),
    UNAUTHORIZED_ERROR    (401, "ERROR - 인증되지 않은 사용자입니다."),
    EMPTY_SECURITY_CONTEXT(401,"Security Context 에 인증 정보가 없습니다."),
    FORBIDDEN_ERROR       (403, "ERROR - 접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR (500,"ERROR - 서버 내부 에러"),
    ;

    private final int statusCode;
    private final String message;

}
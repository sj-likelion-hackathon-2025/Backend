package org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.global.exception.BadRequestException;

class ChallengeCreateServiceRequestTest {

    @DisplayName("값 변환 성공")
    @Test
    void success() {
        getChallengeCreateServiceRequest("2023-01-01", "2023-01-31");
    }

    @DisplayName("값 변환 시 예외 반환")
    @Test
    void throwException() {
        ChallengeCreateServiceRequest request = getChallengeCreateServiceRequest("2023-1-31", "2023-01-01");

        Assertions.assertThatThrownBy(request::toDomainRequest)
                .isInstanceOf(BadRequestException.class);
    }

    private ChallengeCreateServiceRequest getChallengeCreateServiceRequest(String startDate, String endDate) {
        return ChallengeCreateServiceRequest.builder()
                .title("Test Challenge")
                .introduction("Test Introduction")
                .category("DIET")
                .startDate(startDate)
                .endDate(endDate)
                .rule("Test Rule")
                .maxParticipants(4)
                .memberId(1L)
                .build();
    }
}
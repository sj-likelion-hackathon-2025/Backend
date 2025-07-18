package org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeFixture;
import org.kwakmunsu.flowmate.global.exception.BadRequestException;

class ChallengeCreateServiceRequestTest {

    @DisplayName("값 변환 성공")
    @Test
    void success() {
        ChallengeFixture.createChallengeCreateServiceRequest();
    }

    @DisplayName("값 변환 시 예외 반환")
    @Test
    void throwException() {
        ChallengeCreateServiceRequest request = getFailedChallengeCreateServiceRequest();

        Assertions.assertThatThrownBy(request::toDomainRequest)
                .isInstanceOf(BadRequestException.class);
    }

    private ChallengeCreateServiceRequest getFailedChallengeCreateServiceRequest() {
        return ChallengeCreateServiceRequest.builder()
                .title("Test Challenge")
                .introduction("Test Introduction")
                .category("DIET")
                .startDate("2023-1-31")
                .endDate("2023-01-01")
                .rule("Test Rule")
                .maxParticipants(4L)
                .memberId(1L)
                .build();
    }
}
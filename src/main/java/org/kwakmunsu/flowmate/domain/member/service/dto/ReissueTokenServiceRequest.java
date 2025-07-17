package org.kwakmunsu.flowmate.domain.member.service.dto;

import lombok.Builder;

@Builder
public record ReissueTokenServiceRequest(String refreshToken) {

}
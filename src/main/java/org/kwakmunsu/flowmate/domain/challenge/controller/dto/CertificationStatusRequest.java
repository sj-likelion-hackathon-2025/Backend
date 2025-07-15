package org.kwakmunsu.flowmate.domain.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.kwakmunsu.flowmate.domain.member.entity.CertificationStatus;

@Schema(
    description = "인증 상태 요청",
    example = "{\"certificationStatus\":\"PENDING\"}")
public record CertificationStatusRequest(CertificationStatus certificationStatus) {

}
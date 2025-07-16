package org.kwakmunsu.flowmate.domain.member.controller.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record MemberProfileServiceRequest(
        MultipartFile profileImage,
        String name,
        Long memberId
) {

}
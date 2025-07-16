package org.kwakmunsu.flowmate.domain.member.service.dto;

import java.util.List;
import lombok.Builder;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;

@Builder
public record MemberCategoryRegisterServiceRequest(List<InterestCategory> categories, Long memberId) {

}
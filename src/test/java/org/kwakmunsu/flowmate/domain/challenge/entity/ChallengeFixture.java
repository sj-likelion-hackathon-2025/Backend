package org.kwakmunsu.flowmate.domain.challenge.entity;

import static org.kwakmunsu.flowmate.global.util.TimeConverter.stringToDate;

import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeListType;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.SortBy;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.ChallengeApplyServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeCreateServiceRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeReadServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.springframework.test.util.ReflectionTestUtils;

public class ChallengeFixture {

    public static ChallengeCreateDomainRequest createChallengeCreateDomainRequest() {
        return createChallengeCreateDomainRequest("Test Title");
    }

    public static ChallengeCreateDomainRequest createChallengeCreateDomainRequest(String title) {
        return ChallengeCreateDomainRequest.builder()
                .title(title)
                .introduction("Test Introduction")
                .category(InterestCategory.valueOf("DIET"))
                .startDate(stringToDate("2023-01-01"))
                .endDate(stringToDate("2023-01-31"))
                .rule("Test Rule")
                .maxParticipants(4L)
                .leaderId(1L)
                .build();
    }

    public static ChallengeCreateServiceRequest createChallengeCreateServiceRequest() {
        return createChallengeCreateServiceRequest("Test Title");
    }

    public static ChallengeCreateServiceRequest createChallengeCreateServiceRequest(String title) {
        return ChallengeCreateServiceRequest.builder()
                .title(title)
                .introduction("Test Introduction")
                .category("DIET")
                .startDate("2023-01-01")
                .endDate("2023-01-31")
                .rule("Test Rule")
                .maxParticipants(4L)
                .memberId(1L)
                .build();
    }

    public static ChallengeReadServiceRequest createChallengeReadServiceRequest(Long memberId) {
        return ChallengeReadServiceRequest.builder()
                .memberId(memberId)
                .sortBy(SortBy.NEWEST)
                .challengeListType(ChallengeListType.RECRUITING)
                .build();
    }

    public static Challenge createChallenge() {
        return Challenge.create(createChallengeCreateDomainRequest());
    }

    public static Challenge createChallenge(Long id) {
        Challenge challenge = Challenge.create(createChallengeCreateDomainRequest());
        ReflectionTestUtils.setField(challenge, "id", id);

        return challenge;
    }


    public static Challenge createChallenge(String title) {
        return Challenge.create(createChallengeCreateDomainRequest(title));
    }


    public static ChallengeApplyServiceRequest createChallengeApplyServiceRequest(String message) {
        return new ChallengeApplyServiceRequest(message, 1L, 1L);
    }

}
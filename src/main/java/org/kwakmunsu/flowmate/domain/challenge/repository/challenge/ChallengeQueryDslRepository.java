package org.kwakmunsu.flowmate.domain.challenge.repository.challenge;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.kwakmunsu.flowmate.domain.challenge.entity.QChallenge.challenge;
import static org.kwakmunsu.flowmate.domain.challenge.entity.QChallengeParticipant.challengeParticipant;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.repository.challenge.dto.ChallengeReadDomainRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengePreviewResponse;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeQueryDslRepository {

    private static final int PAGE_SIZE = 20;
    private final JPAQueryFactory queryFactory;

    public ChallengeListResponse findAll(ChallengeReadDomainRequest request) {
        List<ChallengePreviewResponse> responses = queryFactory
                .select(constructor(
                        ChallengePreviewResponse.class,
                        challenge.id.as("challengeId"),
                        challenge.title,
                        challenge.introduction,
                        challenge.maxParticipants.as("maxParticipantCount"),
                        select(challengeParticipant.count())
                                .from(challengeParticipant)
                                .where(challengeParticipant.challenge.id.eq(challenge.id)),
                        formattedChallengePeriod(challenge.startDate),
                        formattedChallengePeriod(challenge.endDate),
                        select(challengeParticipant.count().gt(0L))
                                .from(challengeParticipant)
                                .where(
                                        challengeParticipant.challenge.id.eq(challenge.id)
                                                .and(challengeParticipant.member.id.eq(request.memberId()))
                                )
                ))
                .from(challenge)
                .where(cursorIdCondition(request))
                .orderBy(challenge.id.desc())
                .limit(PAGE_SIZE + 1)
                .fetch();

        boolean hasNext = responses.size() > PAGE_SIZE;
        List<ChallengePreviewResponse> limitedPage = getLimitedPage(responses, hasNext);
        Long nextCursorOrNull = getNextCursorOrNull(responses, hasNext);

        return ChallengeListResponse.builder()
                .challengePreviewResponses(limitedPage)
                .nextCursorId(nextCursorOrNull)
                .hasNext(hasNext)
                .build();
    }

    private BooleanExpression cursorIdCondition(ChallengeReadDomainRequest request) {
        if (request.lastChallengeId() == null) {
            return null;
        }
        return challenge.id.lt(request.lastChallengeId());
    }

    private StringTemplate formattedChallengePeriod(DatePath<LocalDate> dateDatePath) {
        return Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                dateDatePath,
                ConstantImpl.create("%Y-%m-%d")  // yyyy-MM-dd 형식
        );
    }

    private List<ChallengePreviewResponse> getLimitedPage(List<ChallengePreviewResponse> responses, boolean hasNext) {
        if (hasNext) {
            return responses.subList(0, PAGE_SIZE); // 실제로는 limit 만큼만 반환
        }

        return responses;
    }

    private Long getNextCursorOrNull(List<ChallengePreviewResponse> responses, boolean hasNext) {
        if (hasNext) {
            return responses.getLast().challengeId();
        }

        return null;
    }

}
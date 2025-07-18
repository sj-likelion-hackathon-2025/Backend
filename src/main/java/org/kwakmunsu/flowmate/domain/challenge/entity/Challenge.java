package org.kwakmunsu.flowmate.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kwakmunsu.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.global.entity.BaseTimeEntity;

@Table(name = "challenge")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 100)
    private String introduction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InterestCategory category;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Long maxParticipants;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rules;

    // 반정규화: 리더 ID 중복 저장
    @Column(name = "leader_id", nullable = false)
    private Long leaderId;  // ChallengeParticipant에도 존재

    @Builder(access = AccessLevel.PRIVATE)
    private Challenge(
            String title,
            String introduction,
            InterestCategory category,
            LocalDate startDate,
            LocalDate endDate,
            Long maxParticipants,
            String rules,
            Long leaderId
    ) {
        this.title = title;
        this.introduction = introduction;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.rules = rules;
        this.leaderId = leaderId;
    }

    public static Challenge create(ChallengeCreateDomainRequest request) {
        return Challenge.builder()
                .title(request.title())
                .introduction(request.introduction())
                .category(request.category())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .maxParticipants(request.maxParticipants())
                .rules(request.rule())
                .leaderId(request.leaderId())
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void updateRules(String rules) {
        this.rules = rules;
    }

    public void updateLeader(Long leaderId) {
        this.leaderId = leaderId;
    }

}
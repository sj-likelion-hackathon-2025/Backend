package org.kwakmunsu.flowmate.domain.challenge.entity;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.entity.ApprovalStatus;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.global.entity.BaseTimeEntity;

@Table(name = "challenge_apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChallengeApply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "challenge_id", nullable = false)
    private Long challengeId;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private ChallengeApply(Member member, Long challengeId, String message) {
        this.member = member;
        this.challengeId = challengeId;
        this.message = message;
        this.status = ApprovalStatus.PENDING;
    }

    public static ChallengeApply create(Member member, Long challengeId, String message) {
        requireNonNull(member);
        requireNonNull(challengeId);
        requireNonNull(member);

        return ChallengeApply.builder()
                .member(member)
                .challengeId(challengeId)
                .message(message)
                .build();
    }

    public void approve() {
        state(this.status == ApprovalStatus.PENDING, "수정 하실 수 없습니다.");
        this.status = ApprovalStatus.APPROVED;
    }

    public void reject() {
        state(this.status == ApprovalStatus.PENDING, "수정 하실 수 없습니다.");
        this.status = ApprovalStatus.REJECTED;
    }

}
package org.kwakmunsu.flowmate.domain.challenge.entity;

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
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ChallengeRole;
import org.kwakmunsu.flowmate.domain.challenge.entity.enums.ParticipationStatus;
import org.kwakmunsu.flowmate.domain.member.entity.Member;

@Table(name = "challenge_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChallengeParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipationStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private ChallengeParticipant(Member member, Challenge challenge, ChallengeRole role, ParticipationStatus status) {
        this.member = member;
        this.challenge = challenge;
        this.role = role;
        this.status = status;
    }

    public static ChallengeParticipant create(Member member, Challenge challenge, ChallengeRole role) {
        return ChallengeParticipant.builder()
                .member(member)
                .challenge(challenge)
                .role(role)
                .status(ParticipationStatus.PENDING)
                .build();
    }

}
package org.kwakmunsu.flowmate.domain.member.entity;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kwakmunsu.flowmate.global.entity.BaseTimeEntity;

@Table(name = "member")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(nullable = false)
    private Long point;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Column(name = "refresh_token")
    private String refreshToken;

    private Member(
            String name,
            String email,
            String socialId,
            String profileImgUrl,
            Role role,
            Grade grade,
            SocialType socialType,
            Long point,
            String refreshToken
    ) {
        this.name = name;
        this.email = email;
        this.socialId = socialId;
        this.role = role;
        this.grade = grade;
        this.socialType = socialType;
        this.point = point;
        this.profileImgUrl = profileImgUrl;
        this.refreshToken = refreshToken;
    }

    public static Member createMember(String name, String email, String socialId, SocialType socialType, String profileImgUrl) {
        requireNonNull(name);
        requireNonNull(email);
        requireNonNull(socialId);
        requireNonNull(socialType);

        return new Member(name, email, socialId, profileImgUrl, Role.GUEST, Grade.ROOKIE, socialType, 0L, null);
    }

    public static Member createAdmin(String name, String email, String socialId) {
        requireNonNull(name);
        requireNonNull(email);
        requireNonNull(socialId);

        return new Member(name, email, socialId, null, Role.ADMIN, Grade.LEGEND, SocialType.LOCAL, 1_000_000L, null);
    }

    public void updateBaseInfo(String name, String profileImgUrl) {
        updateName(name);
        updateProfileImgUrl(profileImgUrl);

    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void increasePoint(Long point) {
        requireNonNull(point);
        if (point < 0) {
            // TODO: 커스텀 예외 처리
            throw new IllegalArgumentException("Point cannot be negative");
        }
        this.point += point;
        updateGrade();
    }

    public void decreasePoint(Long point) {
        requireNonNull(point);

        this.point = Math.max(0L, this.point - point);

        updateGrade();
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void upgradeRoleToMember() {
        if (this.role == Role.GUEST) {
            this.role = Role.MEMBER;
        }
    }

    private void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    private void updateGrade() {
        this.grade = Grade.getGradeByPoint(this.point);
    }
}
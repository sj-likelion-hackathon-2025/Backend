package org.kwakmunsu.flowmate.domain.member.entity;

public enum CertificationStatus {

    UNVERIFIED, // 미인증
    VERIFIED,   // 인증
    PENDING,    // 인증 대기중
    REJECTED,   // 인증 거절
}
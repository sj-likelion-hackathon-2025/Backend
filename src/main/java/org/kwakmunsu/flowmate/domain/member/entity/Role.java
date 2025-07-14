package org.kwakmunsu.flowmate.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    GUEST("ROLE_GUEST"),
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN"),
    ;

    private final String value;

}
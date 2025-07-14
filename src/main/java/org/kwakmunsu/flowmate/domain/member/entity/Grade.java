package org.kwakmunsu.flowmate.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    ROOKIE(0),
    JUNIOR(100),
    PRO   (1000),
    MASTER(10000),
    LEGEND(100000),
    ;

    private final int minPoint;

    // TODO: 추후 로직 리팩토링
    public static Grade getGradeByPoint(Long point) {
        Grade result = ROOKIE;
        for (Grade grade : values()) {
            if (point >= grade.minPoint) {
                result = grade;
            } else {
                break;
            }
        }
        return result;
    }

}
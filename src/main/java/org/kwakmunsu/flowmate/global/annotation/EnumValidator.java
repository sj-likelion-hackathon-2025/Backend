package org.kwakmunsu.flowmate.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Object[] enumConstants = annotation.enumClass().getEnumConstants();
        if (enumConstants == null) {
            return false;
        }

        for (Object constant : enumConstants) {
            String enumValue = constant.toString();
            if (value.equals(enumValue) || (annotation.ignoreCase() && value.equalsIgnoreCase(enumValue))) {
                return true;
            }
        }

        return false;
    }
}
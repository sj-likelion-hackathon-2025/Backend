package org.kwakmunsu.flowmate.global.exception;

import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;

public class BadRequestException extends RootException {

    public BadRequestException(ErrorStatus message) {
        super(message);
    }

}
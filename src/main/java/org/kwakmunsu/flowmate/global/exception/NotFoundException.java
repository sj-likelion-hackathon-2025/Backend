package org.kwakmunsu.flowmate.global.exception;

import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;

public class NotFoundException extends RootException {

    public NotFoundException(ErrorStatus message) {
        super(message);
    }

}
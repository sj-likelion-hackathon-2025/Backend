package org.kwakmunsu.flowmate.global.exception;

import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;

public class InternalServerException extends RootException {

    public InternalServerException(ErrorStatus message) {
        super(message);
    }

}
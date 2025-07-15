package org.kwakmunsu.flowmate.global.exception;

import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;

public class UnAuthenticationException extends RootException {

    public UnAuthenticationException(ErrorStatus message) {
        super(message);
    }

}
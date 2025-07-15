package org.kwakmunsu.flowmate.global.exception;

import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;

public class DuplicationException extends RootException {

    public DuplicationException(ErrorStatus message) {
        super(message);
    }

}
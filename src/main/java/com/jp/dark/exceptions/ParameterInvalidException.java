package com.jp.dark.exceptions;

public class ParameterInvalidException extends RuntimeException {
    public ParameterInvalidException(String errorMessage) {
        super(errorMessage);
    }
}

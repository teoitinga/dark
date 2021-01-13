package com.jp.dark.security.exceptions;

public class AcessDeniedException extends RuntimeException {
    public AcessDeniedException(String errorMessage) {
        super(errorMessage);
    }
}

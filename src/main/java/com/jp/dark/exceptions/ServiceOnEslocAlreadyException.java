package com.jp.dark.exceptions;

public class ServiceOnEslocAlreadyException extends RuntimeException {
    public ServiceOnEslocAlreadyException(String errorMessage) {
        super(errorMessage);
    }
}

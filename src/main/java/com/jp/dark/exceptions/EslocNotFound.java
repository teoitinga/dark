package com.jp.dark.exceptions;

public class EslocNotFound extends RuntimeException {
    public EslocNotFound(String errorMessage) {
        super(errorMessage);
    }
}

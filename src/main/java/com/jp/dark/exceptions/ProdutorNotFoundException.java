package com.jp.dark.exceptions;

public class ProdutorNotFoundException extends RuntimeException {
    public ProdutorNotFoundException(String message) {
        super(message);
    }
}

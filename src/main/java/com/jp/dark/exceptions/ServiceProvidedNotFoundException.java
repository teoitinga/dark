package com.jp.dark.exceptions;

public class ServiceProvidedNotFoundException extends RuntimeException {
    public ServiceProvidedNotFoundException() {
        super("This Service is not found.");
    }
}

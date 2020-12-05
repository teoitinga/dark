package com.jp.dark.exceptions;

public class ServiceProvidedNotFoundException extends RuntimeException {
    public ServiceProvidedNotFoundException() {
        super("This Service is not found.");
    }

    public ServiceProvidedNotFoundException(String servico) {
        super("The Service \"".concat(servico).concat("\" is not found."));
    }
}

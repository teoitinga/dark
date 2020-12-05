package com.jp.dark.exceptions;

public class ServiceProvidedAlreadyException extends RuntimeException {
    public ServiceProvidedAlreadyException() {
        super("Este serviço já está cadastrado!");
    }
}

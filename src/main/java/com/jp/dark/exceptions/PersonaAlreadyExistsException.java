package com.jp.dark.exceptions;

public class PersonaAlreadyExistsException extends RuntimeException {
    public PersonaAlreadyExistsException() {
        super("Persona already exists and cannot be overwritten");
    }
}

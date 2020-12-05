package com.jp.dark.exceptions;

public class PersonaNotFoundException extends RuntimeException{
    public PersonaNotFoundException(String message) {
        super(message);
    }
    public PersonaNotFoundException() {
        super("Esta pessoa não está registrada no banco de dados.");
    }
}

package com.jp.dark.exceptions;

public class PersonaNotFoundException extends RuntimeException{
    public PersonaNotFoundException(String message) {
        super("Não há registros com o CPF: " + message + " em nosso banco de dados;");
    }
    public PersonaNotFoundException() {
        super("Esta pessoa não está registrada no banco de dados.");
    }
}

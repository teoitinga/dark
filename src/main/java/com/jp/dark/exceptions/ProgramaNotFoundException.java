package com.jp.dark.exceptions;

public class ProgramaNotFoundException extends RuntimeException {
    public ProgramaNotFoundException() {
        super("O programa informado não existe ou não está cadastrado.");
    }
}

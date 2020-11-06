package com.jp.dark.exceptions;

public class VisitaNotFoundException extends RuntimeException {

    public VisitaNotFoundException() {
        super("Visita não encontrada.");
    }
}

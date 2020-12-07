package com.jp.dark.exceptions;

public class CpfIsNotValidException extends RuntimeException {
    public CpfIsNotValidException() {
        super("O CPF que você iniformaou não é válido!");
    }
}

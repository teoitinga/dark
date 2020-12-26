package com.jp.dark.security.exceptions;

public class InvaliPasswordException extends RuntimeException {
    public InvaliPasswordException() {
        super("A senha que você informou não está correta com a registrada!");
    }
}

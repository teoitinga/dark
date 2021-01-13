package com.jp.dark.security.exceptions;

import lombok.Getter;

@Getter
public class InvaliPasswordException extends RuntimeException {

    public InvaliPasswordException(
    ) {
            super("A senha que voce informou nao esta correta!");
    }
}

package com.jp.dark.exceptions;

public class PriceItemNotFoundException extends RuntimeException {
    public PriceItemNotFoundException() {
        super("Item price not found.");
    }
}

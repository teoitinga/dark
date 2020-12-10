package com.jp.dark.exceptions;

public class FailedOnCreateFolderException extends RuntimeException {
    public FailedOnCreateFolderException(String folderName) {
        super("Não foi possível criar a pasta com o nome " + folderName);
    }
}

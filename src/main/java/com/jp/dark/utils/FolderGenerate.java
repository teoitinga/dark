package com.jp.dark.utils;

import com.jp.dark.exceptions.FailedOnCreateFolderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Slf4j
public class FolderGenerate {

        public static final String PATH = "C:/jp/atendimentos/";

        public static void createFolder(String folderName){
                try {

                        Path path = Paths.get(PATH.concat(folderName));

                        Files.createDirectories(path);
                        log.info("Criando pasta para pessoal: {}", folderName);

                } catch (IOException e) {

                        log.error("Erro ao criar pasta: {}", folderName);
                        throw new FailedOnCreateFolderException(folderName);

                }
        }

}

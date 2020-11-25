package com.jp.dark.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FolderGenerate {

        public static final String PATH = "C:/jp/atendimentos/";

        public static void createFolder(String folderName){
                try {

                        Path path = Paths.get(PATH.concat(folderName));

                        Files.createDirectories(path);

                        System.out.println("Directory is created!");

                } catch (IOException e) {

                        System.err.println("Failed to create directory!" + e.getMessage());

                }
        }

}

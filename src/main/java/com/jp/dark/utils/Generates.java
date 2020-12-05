package com.jp.dark.utils;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Generates {

    public static String keyCode(String id){

        LocalDateTime time = LocalDateTime.now();

        return keyCodeWithDate(id, time);
    }

    public static String keyCodeWithDate(String id, LocalDateTime time){

        String formattedDate = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        StringBuilder codigo = new StringBuilder();

        codigo.append(formattedDate);

        if(!id.equals("")){
            codigo.append("-");
        }

        codigo.append(Generates.normalize(id));

        return codigo.toString();
    }
    public static String normalize(String texto){
        String folderName = texto.toUpperCase();
        //Define o nome da pasta com todos os caracteres maiusculos e sem caracteres especiais
        folderName = Normalizer.normalize(folderName, Normalizer.Form.NFKD)
                .replaceAll("[^\\p{ASCII}]", "");

        return folderName;
    }
    public static String createNumber(){

        Random random = new Random();
        int numero = random.nextInt(1000);

        return String.valueOf(numero);
    }
}

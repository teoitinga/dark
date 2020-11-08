package com.jp.dark.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Generates {

    public static String keyCode(String id){

        LocalDateTime time = LocalDateTime.now();

        return keyCodeWithDate(id, time);
    }

    public static String keyCodeWithDate(String id, LocalDateTime time){

        String formattedDate = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        StringBuilder codigo = new StringBuilder();

        codigo.append(formattedDate);
        codigo.append("-");
        codigo.append(id);

        return codigo.toString();
    }
}

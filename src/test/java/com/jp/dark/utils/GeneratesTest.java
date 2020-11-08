package com.jp.dark.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class GeneratesTest {
    @Test
    @DisplayName("Deve retornar um código para ID com base no valor informado.")
    public void keyCodeWithDateTest(){

        String id = "123";
        LocalDateTime time = LocalDateTime.now();

        //logica de definição do código para compração - Conforme descrito
        //no método estático Generates.keyCodeWithDate(id,time)
        String formattedDate = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        StringBuilder codigo = new StringBuilder();
        codigo.append(formattedDate);
        codigo.append("-");
        codigo.append(id);
        //fim da lógica de definição do código

        //execução
        String response = Generates.keyCodeWithDate(id,time);
        //verificação
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(codigo.toString());
    }
}

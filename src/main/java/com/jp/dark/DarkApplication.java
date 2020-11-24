package com.jp.dark;

import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.repository.ServiceProvidedRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarkApplication.class, args);
	}

}

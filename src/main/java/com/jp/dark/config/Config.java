package com.jp.dark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

@Configuration
public class Config {

    @Bean
    public DateTimeFormatter formater(){
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }
    @Bean
    public DateTimeFormatter formaterPatternddMMyyyy(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}

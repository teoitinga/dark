package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProvidedDTO {

    private String codigo;

    private String descricao;

    private String referency;

    private BigDecimal defaultValue;

    private Integer timeRemaining;
}

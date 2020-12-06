package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PricesDTO {

    private int id;

    @NotNull(message = "Você deve informar o valor unitário do produto vendido.")
    private BigDecimal valorUnitario;

    @NotNull(message = "Você deve informar a quantidade, por exemplo: Quantas @ é o animal vendido")
    private Integer qtdPorUnid;

    @NotEmpty(message = "Você deve descrever o item informado.")
    private String especificacaoCod;

    @NotEmpty(message = "Você deve informar o produtor consultado")
    private String cpfProdutorInformante;
}

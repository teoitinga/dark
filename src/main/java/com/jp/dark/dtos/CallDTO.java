package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallDTO {

    private String codigo;

    @NotEmpty(message = "Deve informar um serviço prestado")
    private String servico;

    @NotNull(message =  "Deve informar um serviço prestado")
    private ServiceProvidedDTO serviceProvided;

    @NotEmpty(message = "Deve informar uma situação ocorrida.")
    private String ocorrencia;

    @NotNull(message = "Deve haver uma visita informada.")
    private VisitaDTO visita;

    @NotEmpty(message = "Deve haver pelo menos 01(um) produtor inscrito.")
    private List<ProdutorMinDTO> produtores;

    @NotNull(message = "Você deve informar o valor do Serviço")
    private BigDecimal valor;

    private String status;

    private LocalDate forecast;

}

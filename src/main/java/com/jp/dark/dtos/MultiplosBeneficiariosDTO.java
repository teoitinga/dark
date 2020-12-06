package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiplosBeneficiariosDTO {

    private Integer id;

    @NotEmpty(message = "Você deve informar pelo menos um beneficiario.")
    private List<BeneficiarioDTO> beneficiarios;

    @NotNull(message = "Você deve informar o programa que foi atendido.")
    private Integer codigoDoPrograma;
}

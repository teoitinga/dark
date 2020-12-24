package com.jp.dark.dtos;

import com.jp.dark.models.entities.Beneficiario;
import com.jp.dark.models.entities.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiplosBeneficiariosDTO {

    @Id
    @Column(nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "Você deve informar o beneficiario.")
    private List<BeneficiarioDTO> beneficiarios;

    @NotNull(message = "Você deve informar o Programa executado.")
    private String codigoDoPrograma;


}

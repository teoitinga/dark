package com.jp.dark.dtos;

import com.jp.dark.models.entities.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiarioDTO {

    private Integer id;
    private String cpfBeneficiario;
    private int quantidade;
    private int codigoDoPrograma;

}

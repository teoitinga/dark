package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutorMinDTO {
    @NotEmpty
    private String cpf;

    @NotEmpty
    private String nome;
}

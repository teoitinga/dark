package com.jp.dark.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProdutorVO implements Serializable {
    private String cpf;
    private String nome;
    private String fone;
    private String endereco;
}

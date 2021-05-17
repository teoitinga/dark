package com.jp.dark.vos;

import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioVO implements Serializable {

    private String cpf;

    private String nome;

    private String telefone;

    private String nascimento;

    private String endereco;

    private String cep;

    private String cidade;

    private String senha;

    private String categoria;

    private String permissao;

    private Boolean enabled;
}

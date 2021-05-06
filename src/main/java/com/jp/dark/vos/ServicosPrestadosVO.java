package com.jp.dark.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicosPrestadosVO implements Serializable{

    private String dataServico;

    private String servico;

    private String nomeProdutor;

    private String cpfProdutor;

    private String enderecoProdutor;

    private String municipioProdutor;

}

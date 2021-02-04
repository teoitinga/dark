package com.jp.dark.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcaoPrestadaVO implements Serializable {
    private String codigo;
    private String produtor;
    private String servico;
    private String tecnico;
    private String status;
    private String valor;
    private String dataDaChamada;

}

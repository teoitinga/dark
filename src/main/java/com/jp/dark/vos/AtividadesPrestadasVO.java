package com.jp.dark.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadesPrestadasVO implements Serializable {
    private String codigoVisita;
    private String local;
    private String dataDaVisita;
    private List<AcaoPrestadaVO> acoes;
    private Integer totalDeChamadas;
    private String codigoChamada;
    private String dataDaChamada;
    private String produtor;
    private String cpfProdutor;
    private String municipioVisita;
    private String servico;
    private String tecnico;
    private String status;
    private String valor;
}

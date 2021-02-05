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
    private List<ProdutorVO> produtores;
    private Integer totalDeChamadas;
    private String municipioVisita;

}

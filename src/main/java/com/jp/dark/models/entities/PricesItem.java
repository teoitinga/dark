package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class PricesItem {
    @Id
    private String id;

    private String especificação;

    private String unidade;

    private String unidadeDescricao;

    private String detalhes;

    @ManyToOne
    private ItemProducao itemDeProducao;
}

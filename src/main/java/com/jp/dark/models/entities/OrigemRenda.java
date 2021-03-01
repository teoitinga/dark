package com.jp.dark.models.entities;

import com.jp.dark.models.enums.EnumSimNao;
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
public class OrigemRenda {

    @Id
    private String codigo;
    private String descricao;
    private String referencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_agro")
    private EnumSimNao isAgro;

}

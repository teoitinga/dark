package com.jp.dark.models.entities;

import com.jp.dark.dtos.ProdutorMinDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Call extends Auditable{
    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotEmpty(message = "Você deve informar um serviço.")
    @Column
    private String servico;

    @Column
    private String ocorrencia;

    @ManyToOne
    private Visita visita;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Persona> produtores;
}

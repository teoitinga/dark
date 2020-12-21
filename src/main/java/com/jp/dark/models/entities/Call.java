package com.jp.dark.models.entities;

import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.enums.EnumStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@EqualsAndHashCode
public class Call extends Auditable implements Cloneable {

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotEmpty(message = "Você deve descrever o serviço prestado.")
    @Column
    private String servico;

    @ManyToOne
    private ServiceProvided serviceProvided;

    @Column
    private String ocorrencia;

    @ManyToOne
    private Visita visita;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    @Column
    private BigDecimal valor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Persona responsavel;

    private LocalDate previsaoDeConclusao;

    private LocalDate servicoQuitadoEm;

    @Override
    public Call clone() throws CloneNotSupportedException {
        return (Call) super.clone();
    }
}

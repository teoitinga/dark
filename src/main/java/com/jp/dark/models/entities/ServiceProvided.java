package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class ServiceProvided extends Auditable{
    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, unique = true, length = 155)
    private String descricao;

    @Column(nullable = false, unique = true, length = 155)
    private String referency;

    @Column
    private BigDecimal defaultValue;

    @Column
    private Integer timeRemaining;
}

package com.jp.dark.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class ServiceProvided{
    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, unique = false, length = 155)
    private String descricao;

    @Column(nullable = false, unique = false, length = 155)
    private String referency;

    @Column
    private BigDecimal defaultValue;

    @Column
    private Integer timeRemaining;

    public ServiceProvided(String codigo, String descricao, String referency, BigDecimal defaultValue, Integer timeRemaining) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.referency = referency;
        this.defaultValue = defaultValue;
        this.timeRemaining = timeRemaining;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="service_provided_escritorio",
            joinColumns={
                    @JoinColumn(name="service_provided_codigo", referencedColumnName="codigo")},
            inverseJoinColumns={
                    @JoinColumn(name="escritorio_codigo", referencedColumnName="codigo")})
    private List<Escritorio> esloc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceProvided)) return false;

        ServiceProvided that = (ServiceProvided) o;

        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}

package com.jp.dark.models.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Escritorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false, unique = true)
    private int codigo;

    @Column(nullable = false, unique = false, length = 155)
    private String descricao;

    @Column(nullable = false, unique = false, length = 155)
    private String referency;

    @Column(nullable = false, unique = false, length = 155)
    private String endereco;

    @Column(nullable = false, unique = false, length = 155)
    private String municipio;

    @Column(nullable = false, unique = false, length = 155)
    private String fone;

    @Column(nullable = false, unique = false, length = 155)
    private String zap;

    @Column(nullable = false, unique = false, length = 155)
    private String email;

    @ManyToMany( targetEntity = ServiceProvided.class  , mappedBy = "esloc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ServiceProvided> servicos;

    @ToString.Exclude
    @OneToMany(targetEntity = Persona.class  , mappedBy = "esloc", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Persona> publico = new ArrayList<>();

    public Escritorio(
            String escritorio,
            String email,
            String endereco,
            String contato,
            String municipio,
            String referency,
            String zap
    ){
      this.descricao = escritorio;
      this.email = email;
      this.endereco = endereco;
      this.fone = contato;
      this.municipio = municipio;
      this.referency = referency;
      this.zap = zap;
    }

    public void addService(ServiceProvided service){
        this.servicos.add(service);
        service.getEsloc().add(this);
    }
    public void removeService(ServiceProvided service){

        //remove o serviço informado do Array de serviços
        this.servicos.remove(service);

        //removo o esloc do serviço prestado
        service.getEsloc().remove(this);
    }

    public void removePersona(Persona persona){
        this.publico.remove(persona);
        persona.setEsloc(null);
    }

    public void addPersona(Persona persona) {
        this.publico.add(persona);
        persona.setEsloc(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Escritorio that = (Escritorio) o;

        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return codigo;
    }
}

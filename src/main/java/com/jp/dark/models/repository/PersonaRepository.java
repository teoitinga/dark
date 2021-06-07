package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, String> {

    boolean existsByCpf(String cpf);

    Optional<Persona> findByCpf(String cpf);

    List<Persona> findByNomeContainingIgnoreCaseAndPermissao(String name, EnumPermissao permissao);

    List<Persona> findByNomeContainingIgnoreCaseAndPermissaoNot(String name, EnumPermissao permissao);

    @Query(value = "SELECT p " +
            "FROM Persona p " +
            "WHERE " +
            "( LOWER(p.nome) LIKE LOWER(concat('%', :name,'%')) ) " +
            "AND (p.permissao IS NOT :permissao) " +
            "AND (p.esloc.codigo = :codEsloc)"
    )
    List<Persona> findByNomeUserByEsloc(String name, EnumPermissao permissao, int codEsloc);
}

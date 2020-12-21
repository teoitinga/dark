package com.jp.dark.services;

import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Visita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VisitaService {
    public VisitaDTO save(VisitaDTO visitaDto);

    abstract Visita findByCodigo(String codigo);

    List<Call> verifyCalls(List<CallDTOPost> chamadas, Visita visita);

    List<Persona> verifyProdutores(List<ProdutorMinDTO> produtores);

    Persona check(ProdutorMinDTO prd);

    boolean personaExists(String cpf);

    boolean cpfIsValid(String cpf);

    VisitaDTO getByCodigo(String codigo);

    VisitaDTO toVisitaDTO(Visita vs);

    Page<VisitaDTO> find(VisitaDTO dto, Pageable pageRequest);

    void delete(Visita visita);

    Visita save(Visita visita);
}

package com.jp.dark.services.impls;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.services.VisitaService;
import com.jp.dark.utils.Generates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitaServiceImpl implements VisitaService {

    private VisitaRepository repository;

    public VisitaServiceImpl(VisitaRepository repository) {

        this.repository = repository;
    }

    @Override
    public VisitaDTO save(VisitaDTO visita) {
        if( repository.existsByCodigo(visita.getCodigo()) ){
            throw new BusinessException("Identificador já existe");
        }

        Visita entity = toVisita(visita);
        entity.setCodigo(createId(visita.getCodigo()));
        entity = repository.save(entity);

        return toVisitaDto(entity);
    }
    @Override
    public String createId(String key){

        return Generates.keyCode(key);
    }

    @Override
    public VisitaDTO toVisitaDto(Visita visita) {
        String codigo;
        try{
            codigo = visita.getCodigo();
        }catch (Exception e){
            codigo = "";
        }
        String recomendacao;
        try{
            recomendacao= visita.getRecomendacao();
        }catch (Exception e){
            recomendacao = "";
        }
        String situacao;
        try{
            situacao = visita.getSituacao();
        }catch (Exception e){
            situacao = "";
        }
        VisitaDTO build = VisitaDTO.builder()
                .codigo(codigo)
                .recomendacao(recomendacao)
                .situacao(situacao)
                .build();

        return build;
    }

    @Override
    public Visita toVisita(VisitaDTO visita) {
        String codigo;
        try{
            codigo = visita.getCodigo();
        }catch (Exception e){
            codigo = "";
        }

        String recomendacao;
        try{
        recomendacao = visita.getRecomendacao();
        }catch (NullPointerException ex){
            recomendacao = null;
        }

        String situacao;
        try{
        situacao = visita.getSituacao();
        }catch (NullPointerException ex){
            situacao = null;
        }
        return Visita.builder()
                .codigo(codigo)
                .recomendacao(recomendacao)
                .situacao(situacao)
                .build();
    }

    @Override
    public Optional<VisitaDTO> getByCodigo(String codigo) {

        Optional<Visita> visita = repository.findByCodigo(codigo);

        if (visita.isPresent()){
            return Optional.of(toVisitaDto(visita.get()));
        }
        return Optional.empty();
    }

    @Override
    public VisitaDTO update(VisitaDTO visita) {
        Visita vs = this.toVisita(visita);
        vs = repository.save(vs);

        return this.toVisitaDto(vs);
    }

    @Override
    public void delete(Visita visita) {
        if(visita == null || visita.getCodigo() ==null){
            throw new VisitaNotFoundException();
        }
        this.repository.delete(visita);
    }

    @Override
    public Page<VisitaDTO> find(VisitaDTO filter, Pageable pageRequest) {

        Visita vs = this.toVisita(filter);

        Example<Visita> example = Example.of(vs,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<Visita> pages = repository.findAll(example, pageRequest);

        List<VisitaDTO> collect = pages.getContent()
                .stream()
                .map(entity -> this.toVisitaDto(entity))
                .collect(Collectors.toList());

        return new PageImpl<VisitaDTO>(collect, pageRequest, pages.getTotalElements());
    }
}

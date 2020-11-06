package com.jp.dark.services.impls;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.VisitaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VisitaServiceImpl implements com.jp.dark.services.VisitaService {

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
        String key = "123";
        entity.setCodigo(createId(key));
        entity = repository.save(entity);

        return toVisitaDto(entity);
    }
    @Override
    public String createId(String key){
        return "20201104";
    }

    @Override
    public VisitaDTO toVisitaDto(Visita entity) {
        String codigo;
        try{
            codigo = entity.getCodigo();
        }catch (Exception e){
            codigo = "";
        }
        String recomendacao;
        try{
            recomendacao= entity.getRecomendacao();
        }catch (Exception e){
            recomendacao = "";
        }
        String situacao;
        try{
            situacao = entity.getSituacao();
        }catch (Exception e){
            situacao = "";
        }

        return VisitaDTO.builder()
                .codigo(codigo)
                .recomendacao(recomendacao)
                .situacao(situacao)
                .build();

    }
    @Override
    public Visita toVisita(VisitaDTO visita) {
        String codigo;
        try{
            codigo = visita.getCodigo();
        }catch (Exception e){
            codigo = "";
        }

        return Visita.builder()
                .codigo(codigo)
                .recomendacao(visita.getRecomendacao())
                .situacao(visita.getSituacao())
                .build();
    }

    @Override
    public Optional<VisitaDTO> getByCodigo(String codigo) {

         return repository.findByCodigo(codigo);
    }

    @Override
    public VisitaDTO update(VisitaDTO visita) {
        Visita vs = this.toVisita(visita);
        vs = repository.save(vs);

        return this.toVisitaDto(vs);
    }
}

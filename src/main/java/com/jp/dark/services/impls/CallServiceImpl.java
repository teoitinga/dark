package com.jp.dark.services.impls;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.services.CallService;
import com.jp.dark.services.VisitaService;
import com.jp.dark.utils.Generates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallServiceImpl implements CallService {

    private VisitaService visitaService;

    private CallRepository repository;

    @Override
    public CallDTO save(CallDTO call) {

        //Configura o código da chamada
        String keyCode = Generates.keyCode("123");
        call.setCodigo(keyCode);

        //verifica se a visita existes
        //VisitaDTO vs = visitaService.getByCodigo(call.getCodigoVisita()).orElseThrow(()-> new VisitaNotFoundException());

        //converte de DTO para chamada
        Call entity = toCall(call);

        //salva
        return toCallDto(repository.save(entity));
    }

    private CallDTO toCallDto(Call call) {
        return CallDTO.builder()
                .codigo(call.getCodigo())
                .codigoVisita(call.getVisita().getCodigo())
                .servico(call.getServico())
                .ocorrencia(call.getOcorrencia())
                .build();
    }

    private Call toCall(CallDTO callDto) {

        //Verifica se existe a visita informada
        VisitaDTO vs = visitaService.getByCodigo(callDto.getCodigo()).orElseThrow(()-> new VisitaNotFoundException());
        Visita visita = visitaService.toVisita(vs);

        return Call.builder()
                .codigo(callDto.getCodigo())
                .ocorrencia(callDto.getOcorrencia())
                .visita(visita)
                .servico(callDto.getServico())
                .build();
    }
}

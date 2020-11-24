package com.jp.dark.services;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;

import java.util.List;

public interface CallService {

    CallDTO save(CallDTO call);

    List<Persona> toPersona(List<ProdutorMinDTO> produtores);

    List<ProdutorMinDTO> checkProdutores(List<ProdutorMinDTO> produtores);

    ProdutorMinDTO check(ProdutorMinDTO produtor);

    CallDTO toCallDto(Call call);

    Call toCall(CallDTO callDto);
}

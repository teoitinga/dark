package com.jp.dark.services;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Visita;

import java.util.List;

public interface CallService {
    List<CallDTOPost> toCallDTOPost(List<Call> chamadas);

    CallDTOPost toCallDTOPost(Call c);

    CallDTO save(CallDTO dto, Visita vs);

    Call toCall(CallDTO dto, Visita codigoDaVisita);

    CallDTO toCallDTO(Call call);

    Call toCall(CallDTOPost call);

    List<Call> toCall(List<CallDTOPost> chamadas);

    List<Call> save(List<Call> call);

    Call Save(Call call);

    Call toCall(Call call, Visita vs);
}

package com.jp.dark.services;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.dtos.CallDTOView;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.vos.AtividadesPrestadasVO;
import com.jp.dark.vos.CallPesquisaVO;
import com.jp.dark.vos.CallVO;
import com.jp.dark.vos.ServicosPrestadosVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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

    Call save(Call call);

    Call toCall(Call call, Visita vs);

    CallDTOPost save(CallDTOPost dto);

    CallDTOPost update(CallDTOPost dto, String id);

    CallDTOPost cancel(String id);

    CallDTOPost finalize(String id);

    CallDTOPost initialize(String id);

    CallDTOPost updateValue(String id, BigDecimal valur);

    Page<CallDTOPost> getCalls(Pageable pageRequest);

    Call toCall(CallDTOPost call, Visita vs);

    Page<CallDTOView> getCallsView(Pageable pageRequest);

    Integer getCallsOperation();
    Page<Call> findAllCalls(Pageable pageRequest);

    List<Call> findAllCallsUser(Pageable pageRequest);

    Call toCall(CallVO vo, Visita visita);

    CallVO toCallVO(Call call);

    Page<ServicosPrestadosVO> getServicos(Pageable pageRequest);

    List<AtividadesPrestadasVO> getAtividades(String dataInicial, String dataFinal);
}

package com.jp.dark.controllers;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.dtos.CallDTOView;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.ParameterInvalidException;
import com.jp.dark.exceptions.ServiceProvidedNotFoundException;
import com.jp.dark.services.CallService;
import com.jp.dark.services.VisitaService;
import com.jp.dark.vos.AtividadesPrestadasVO;
import com.jp.dark.vos.CallPesquisaVO;
import com.jp.dark.vos.ServicosPrestadosVO;
import com.jp.dark.vos.ServicosReportVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chamadas")
@Slf4j
public class CallController {

    private CallService callService;

    private VisitaService visitaService;

    public CallController(
            CallService callService,
            VisitaService visitaService
    ) {
        this.callService = callService;
        this.visitaService = visitaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CallDTOPost save(@RequestBody @Valid CallDTOPost dto){
        return callService.save(dto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CallDTOPost update(@PathVariable String id, @RequestBody @Valid CallDTOPost dto){
        return callService.update(dto, id);
    }
    @PutMapping("initialize/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CallDTOPost callInitialize(@PathVariable String id){
        return callService.initialize(id);
    }
    @PutMapping("cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CallDTOPost callCancel(@PathVariable String id){
        return callService.cancel(id);
    }
    @PutMapping("finalize/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CallDTOPost callFinalize(@PathVariable String id){
        return callService.finalize(id);
    }
    @PutMapping("expirated/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CallDTOPost callExpirated(@PathVariable String id){
        return callService.expirate(id);
    }
    @PutMapping("update/{id}/{value}")
    @ResponseStatus(HttpStatus.OK)
    public CallDTOPost callUpdateValue(@PathVariable String id, @PathVariable BigDecimal value){
        return callService.updateValue(id, value);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET my calls")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Page<CallDTOView> myCallsView(Pageable pageRequest){
        return this.callService.getCallsView(pageRequest);
    }

    @GetMapping("count")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET my calls in operation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Integer myCallsOpen(){
        return this.callService.getCallsOperation();
    }

    @GetMapping("relatorio")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("GET all servicos for report")
    public Page<ServicosPrestadosVO> getServicos(Pageable pageRequest){

        return this.callService.getServicos(pageRequest);
    }

    @GetMapping("relatorioReportMensal")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("GET all servicos for report actual month")
    public ServicosReportVO getServicosMensais(@RequestParam String esloc, @RequestParam String mes){
        try{

            return this.callService.getServicosReportMensal(Integer.parseInt(esloc), mes);

        }catch (NumberFormatException e){
            throw new ParameterInvalidException("Parametros incorretos ou não esperados");
        }
    }
    @GetMapping("relatorioReportAnual")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("GET all servicos for report actual YEAR")
    public ServicosReportVO getServicosAno(@RequestParam String esloc){
        try{

            return this.callService.getServicosReportAnual(Integer.parseInt(esloc));

        }catch (NumberFormatException e){
            throw new ParameterInvalidException("Parametros incorretos ou não esperados");
        }
    }
    @GetMapping("relatorioDiario")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("GET all servicos for report actual day")
    public ServicosReportVO getDiarioServicos(@RequestParam String esloc){
        try{

            return this.callService.getDiarioServicos(Integer.parseInt(esloc));

        }catch (NumberFormatException e){
            throw new ParameterInvalidException("Parametros incorretos ou não esperados");
        }
    }
    @GetMapping("relatorioReportUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("GET all servicos for report actual day")
    public ServicosReportVO getServicosByUserToday(){
            return this.callService.getServicosByUserTodayReport();
    }

    @GetMapping("gerenciar")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET all atividades ordered")
    public List<AtividadesPrestadasVO> getAtividades( @RequestParam String dataInicial, @RequestParam String dataFinal){
        return this.callService.getAtividades(dataInicial, dataFinal);
    }
}

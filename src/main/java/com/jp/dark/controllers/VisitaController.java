package com.jp.dark.controllers;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Call;
import com.jp.dark.services.VisitaService;
import com.jp.dark.vos.CallVO;
import com.jp.dark.vos.VisitaListVO;
import com.jp.dark.vos.VisitaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/visitas")
@Slf4j
@Api("Api visitas")
public class VisitaController {

    private VisitaService service;

    public VisitaController(VisitaService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid Visita")
    public VisitaDTO create(@RequestBody @Valid VisitaDTO dto){

        log.info("Registrando visita: {}", dto);
        return service.save(dto);
    }

    @GetMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET a Visita BY id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public VisitaDTO getDetails(@PathVariable String codigo){
        return service.getByCodigo(codigo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET a Visita")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Page<VisitaDTO> find( VisitaDTO dto, Pageable pageRequest){
        return service.find(dto, pageRequest);
    }

    @GetMapping("/manager")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET aLL Visita")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Page<VisitaVO> findAllVisitaManager(Pageable pageRequest){
        return service.findAllVisitaManager(pageRequest);
    }

    @GetMapping("/user-manager")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET All Visita por usuario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Page<VisitaListVO> findAllVisitaManagerUser(Pageable pageRequest){
        return service.findAllVisitaManagerUser(pageRequest);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET aLL Visita")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public CallVO findAllVisitaManagerUser(@RequestBody CallVO vo, @PathVariable String id){
        return service.includeCall(vo, id);
    }
}

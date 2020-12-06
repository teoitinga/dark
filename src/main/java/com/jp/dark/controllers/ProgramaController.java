package com.jp.dark.controllers;

import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ProgramaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/programas")
@Slf4j
@Api("Api de programas e ações")
public class ProgramaController {

    private ProgramaService service;

    public ProgramaController(ProgramaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid Visita")
    public MultiplosBeneficiariosDTO create(@RequestBody @Valid MultiplosBeneficiariosDTO dto){
        return this.service.save(dto);
    }
}

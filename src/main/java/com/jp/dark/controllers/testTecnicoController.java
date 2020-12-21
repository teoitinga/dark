package com.jp.dark.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/tecnico")
@Slf4j
@Api("Api teste role tecnico")
public class testTecnicoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid Visita")
    public String create(@RequestBody @Valid String texto){
        return "Ola tecnico, " + texto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("CREATE a valid Visita")
    public String getDetails(){
        return "Ola tecnico, teste com sucesso";
    }
}

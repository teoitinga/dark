package com.jp.dark.controllers;

import com.jp.dark.dtos.VisitaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cedido")
@Slf4j
@Api("Api teste role cedido")
@PreAuthorize("hasRole('ROLE_CEDIDO')")
public class testUserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid Visita")
    public String create(@RequestBody @Valid String texto){
        return "Ola, "+texto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("CREATE a valid Visita")
    public String getDetails(){
        return "Ola, teste com sucesso";
    }
}

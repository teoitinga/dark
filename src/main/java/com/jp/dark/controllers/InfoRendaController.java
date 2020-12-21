package com.jp.dark.controllers;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.InfoRendaDTO;
import com.jp.dark.services.InfoRendaService;
import com.jp.dark.services.impls.InfoRendaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/inforenda")
@Slf4j
@Api("Api para levantamento de renda e produção.")
public class InfoRendaController {


    private InfoRendaService service;
    private Config config;

    public InfoRendaController(Config config,
                               InfoRendaService service) {
        this.config = config;
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid info renda")
    public InfoRendaDTO create(@RequestBody @Valid InfoRendaDTO dto){

        return service.save(dto);
    }
}

package com.jp.dark.controllers;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.InfoRendaDTO;
import com.jp.dark.dtos.ItemProducaoDTO;
import com.jp.dark.dtos.OrigemRendaDTO;
import com.jp.dark.dtos.ProgramaDTO;
import com.jp.dark.models.entities.ItemProducao;
import com.jp.dark.models.entities.OrigemRenda;
import com.jp.dark.services.InfoRendaService;
import com.jp.dark.services.impls.InfoRendaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @GetMapping("/search-producao/{item}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Return a list of finding item produccao")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public List<ItemProducaoDTO> findByDescricaoContaining(@PathVariable String item){
        return service.findByDescricaoContaining(item);
    }
    @GetMapping("/search-origem/{org}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Return a list of finding item produccao")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public List<OrigemRendaDTO> findOrigemByDescricaoContaining(@PathVariable String org){
        return service.findOrigemByDescricaoContaining(org);
    }
}

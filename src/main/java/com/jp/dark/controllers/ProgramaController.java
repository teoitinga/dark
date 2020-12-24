package com.jp.dark.controllers;

import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.dtos.ProgramaDTO;
import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.services.ProgramaService;
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
    @ApiOperation("CREATE a valid programa")
    public MultiplosBeneficiariosDTO create(@RequestBody @Valid MultiplosBeneficiariosDTO dto){
        return this.service.save(dto);
    }
    @PostMapping("/programa")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid program")
    public ProgramaDTO create(@RequestBody @Valid ProgramaDTO dto){
        return this.service.createProgram(dto);
    }
    @GetMapping("/search/{prg}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Return a list of finding programa municipal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public List<ProgramaDTO> findByServiceContaining(@PathVariable String prg){
        return service.findByReferenciaContaining(prg);
    }
}

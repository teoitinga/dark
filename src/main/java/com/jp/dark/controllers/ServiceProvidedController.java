package com.jp.dark.controllers;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.repository.ServiceProvidedRepository;
import com.jp.dark.services.ProdutorService;
import com.jp.dark.services.ServiceProvidedService;
import com.jp.dark.services.impls.ServiceProvidedServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/services")
@Slf4j
@Api("Api de Serviços")
public class ServiceProvidedController {

    private ServiceProvidedService service;

    public ServiceProvidedController(ServiceProvidedRepository repository) {
        this.service = new ServiceProvidedServiceImpl(repository);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a Service")
    @ApiResponses({
            @ApiResponse(code = 201, message = "")
    })
    public ServiceProvidedDTO save(@RequestBody @Valid ServiceProvidedDTO dto){
        return service.save(dto);
    }

}

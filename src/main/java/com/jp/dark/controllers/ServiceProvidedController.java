package com.jp.dark.controllers;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.ServiceProvidedAlreadyException;
import com.jp.dark.models.repository.ServiceProvidedRepository;
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
import java.util.List;

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
    @GetMapping("{srv}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Return a list of finding service")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public List<ServiceProvidedDTO> findByServiceContaining(@PathVariable String srv){
        return service.findByServiceContaining(srv);
    }
    @ExceptionHandler(ServiceProvidedAlreadyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleServiceProvidedAlreadyException(ServiceProvidedAlreadyException exception){
        return new ApiErrors(exception);
    }
}

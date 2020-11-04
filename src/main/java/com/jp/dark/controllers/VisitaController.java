package com.jp.dark.controllers;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.services.VisitaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/visitas")
@Slf4j
public class VisitaController {

    private VisitaService service;

    public VisitaController(VisitaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitaDTO create(@RequestBody @Valid VisitaDTO dto){
        log.info("Registrndo: {}", dto);
        return service.save(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException exception){
        return new ApiErrors(exception);
    }
}

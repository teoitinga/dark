package com.jp.dark.controllers;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.ServiceProvidedNotFoundException;
import com.jp.dark.services.CallService;
import com.jp.dark.services.VisitaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/chamadas")
@Slf4j
public class CallController {

    private CallService callService;

    private VisitaService visitaService;

    public CallController(
            CallService callService,
            VisitaService visitaService
    ) {
        this.callService = callService;
        this.visitaService = visitaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CallDTOPost save(@RequestBody @Valid CallDTOPost dto){
        return callService.save(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ConstraintViolationException exception){

        return new ApiErrors(exception);
    }
    @ExceptionHandler(ServiceProvidedNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ServiceProvidedNotFoundException exception){

        return new ApiErrors(exception);
    }

}

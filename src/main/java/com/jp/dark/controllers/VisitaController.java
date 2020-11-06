package com.jp.dark.controllers;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.services.VisitaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        return service.save(dto);
    }

    @GetMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public VisitaDTO getDetails(@PathVariable String codigo){
        return service.getByCodigo(codigo).orElseThrow(()->new VisitaNotFoundException());
    }
    @PutMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public VisitaDTO update(@PathVariable String codigo, VisitaDTO dto){
        VisitaDTO visita = service.getByCodigo(codigo).orElseThrow(() -> new VisitaNotFoundException());
        visita.setRecomendacao(dto.getRecomendacao());
        visita.setSituacao(dto.getSituacao());

        visita = service.update(visita);

        return visita;

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

    @ExceptionHandler(VisitaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleVisitaNotFoundException(VisitaNotFoundException exception){
        return new ApiErrors(exception);
    }
}

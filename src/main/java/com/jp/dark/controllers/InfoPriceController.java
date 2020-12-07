package com.jp.dark.controllers;

import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.services.InfoPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/infoprice")
@Slf4j
@Api("Api Lançamento de informações sobre preços atuais")
public class InfoPriceController {

    private InfoPriceService service;

    public InfoPriceController(InfoPriceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("REGISTER info of prices")
    public PricesDTO create(@RequestBody @Valid PricesDTO dto){
        return service.save(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }
}

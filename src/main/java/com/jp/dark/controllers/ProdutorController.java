package com.jp.dark.controllers;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.services.ProdutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/produtores")
@Slf4j
@Api("Api de produtores")
public class ProdutorController {

    private ProdutorService service;

    public ProdutorController(ProdutorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a Produtor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "")
    })
    public ProdutorDTO save(@RequestBody @Valid ProdutorDTO dto){
        return service.save(dto);
    }
    /*
    /* ExceptionHandlers
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(PersonaAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlePersonaAlreadyExistsException(PersonaAlreadyExistsException exception){
        return new ApiErrors(exception);
    }
}

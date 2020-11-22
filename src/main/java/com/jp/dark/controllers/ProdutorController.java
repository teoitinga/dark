package com.jp.dark.controllers;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.services.ProdutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/produtores")
@Slf4j
public class ProdutorController {

    private ProdutorService service;

    public ProdutorController(ProdutorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutorDTO save(@RequestBody @Valid ProdutorDTO dto){

        return service.save(dto);
    }
}

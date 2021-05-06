package com.jp.dark.controllers;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.exceptions.ApiErrors;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.impls.PersonaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produtores")
@Slf4j
@Api("Api de produtores")
public class ProdutorController {

    private PersonaService service;
    PasswordEncoder passwordEncoder;
    public ProdutorController(PersonaRepository repository,  PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;

        this.service = new PersonaServiceImpl(repository, passwordEncoder);
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

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find produtor by name containing")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public List<ProdutorDTO> findByName(@PathVariable String name){
        return this.service.findProdutorByNameContaining(name);
    }

    @GetMapping("find/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find produtor by CPF")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public ProdutorDTO findByCpf(@PathVariable String cpf){
        return this.service.findProdutorByCpf(cpf);
    }

    @PutMapping("{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("update a Produtor rural")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public PersonaDTO update(@PathVariable String cpf, @RequestBody PersonaDTO dto){

        return service.update(cpf, dto);
    }

}

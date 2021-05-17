package com.jp.dark.controllers;

import com.jp.dark.dtos.EslocDTO;
import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.services.EslocService;
import com.jp.dark.vos.UsuarioVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/esloc")
@Slf4j
@Api("Api Registro de Escritórios locais")
public class EscritorioController {

    private EslocService eslocService;

    public EscritorioController(EslocService eslocService) {
        this.eslocService = eslocService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("REGISTER info of eslocs")
    public EslocDTO create(@RequestBody @Valid EslocDTO dto){
        return eslocService.save(dto);
    }

    @PostMapping("/addService")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("REGISTER services for eslocs")
    public void addService(@RequestParam String codEsloc, @RequestParam String codService){
        eslocService.addService(codEsloc, codService);
    }

    @DeleteMapping("/rmService")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("REGISTER services for eslocs")
    public void removeService(@RequestParam String codEsloc, @RequestParam String codService){
        eslocService.removeService(codEsloc, codService);
    }

    @PostMapping("/addUsuario")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("REGISTER users for eslocs")
    public void addFuncionario(@RequestParam String codEsloc, @RequestParam String cpf){
        eslocService.addUser(codEsloc, cpf);
    }

    @DeleteMapping("/rmUsuario")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("REGISTER users for eslocs")
    public void removeFuncionario(@RequestParam String codEsloc, @RequestParam String cpf){
        eslocService.removeUser(codEsloc, cpf);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET a users for this escritorio")
    public List<UsuarioVO> getUsueriosForEsloc(@RequestParam String codEsloc){
        return eslocService.getUsersForEsloc(codEsloc);
    }

    @GetMapping("/eslocs")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET all eslocs")
    public List<EslocDTO> getEslocs(){
        return eslocService.getAllEslocs();
    }


}

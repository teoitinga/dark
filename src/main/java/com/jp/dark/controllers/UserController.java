package com.jp.dark.controllers;

import com.jp.dark.dtos.UserDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.services.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Api("Api de gerenciamento de usuarios")
@PreAuthorize("hasRole('ROLE_TECNICO')")
public class UserController {

    private PersonaService service;

    public UserController(PersonaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE a valid User")
    public UserDTO create(@RequestBody @Valid UserDTO dto){

        return service.save(dto);
    }
    @GetMapping("{login}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("CREATE a valid User")
    public UserDTO details(@PathVariable String login){

        return service.getDetailsUser(login);
    }
}

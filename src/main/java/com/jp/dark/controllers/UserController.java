package com.jp.dark.controllers;

import com.jp.dark.dtos.CredenciaisDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.dtos.TokenDTO;
import com.jp.dark.dtos.UserDTO;
import com.jp.dark.security.AuthenticationService;
import com.jp.dark.services.PersonaService;
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
@RequestMapping("/api/v1/users")
@Slf4j
@Api("Api de gerenciamento de usuarios")
public class UserController {

    private PersonaService service;


    public UserController(PersonaService service
    ) {
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
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find produtor by name containing")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public List<UserDTO> findByName(@PathVariable String name){
        return this.service.findUserByNameContaining(name);
    }


}

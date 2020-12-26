package com.jp.dark.controllers;

import com.jp.dark.dtos.CredenciaisDTO;
import com.jp.dark.dtos.TokenDTO;
import com.jp.dark.security.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Api("Api de autenticação de usuarios")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Do login by user")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public TokenDTO authentication(@RequestBody @Valid CredenciaisDTO credenciais){
        TokenDTO token = this.authenticationService.autenticar(credenciais);
        return token;
    }
}

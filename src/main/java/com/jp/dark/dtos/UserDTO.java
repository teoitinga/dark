package com.jp.dark.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {

    private String login;
    private String name;
    private String password;
    private String role;
    private String municipio;
    private String contato;

}

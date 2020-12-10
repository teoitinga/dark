package com.jp.dark.factory;

import com.jp.dark.dtos.UserDTO;

public class UserFactory {
    public static UserDTO createAnyUser() {
        return UserDTO.builder()
                .login("82322892602")
                .name("Nicolas Enzo Lorenzo Viana")
                .municipio("Tarumirim")
                .role("TECNICO")
                .password("123")
                .contato("3332331530")
                .build();
    }
}

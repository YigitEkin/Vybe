package com.vybe.backend.model.dto;

import lombok.Data;

@Data
public class SignInDTO {
    private String username;
    private String password;
    private String code;
}

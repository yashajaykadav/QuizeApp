package org.yash.quize_app.dto;

import lombok.Data;

@Data
public class LoginRequest {

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Email
    private String email;

    @jakarta.validation.constraints.NotBlank
    private String password;
}

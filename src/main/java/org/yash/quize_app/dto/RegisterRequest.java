package org.yash.quize_app.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @jakarta.validation.constraints.NotBlank
    private String name;

    private String role;

    @jakarta.validation.constraints.NotBlank
    private String password;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Email
    private String email;
}

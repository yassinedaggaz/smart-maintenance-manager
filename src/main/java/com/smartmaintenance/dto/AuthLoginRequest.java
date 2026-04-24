package com.smartmaintenance.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginRequest {

    @NotBlank(message = "Username est obligatoire")
    private String username;

    @NotBlank(message = "Password est obligatoire")
    private String password;
}

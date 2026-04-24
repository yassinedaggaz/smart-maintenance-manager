package com.smartmaintenance.dto;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterRequest {

    @NotBlank(message = "Username est obligatoire")
    @Size(min = 3, max = 50, message = "Username doit contenir entre 3 et 50 caractères")
    private String username;

    @NotBlank(message = "Email est obligatoire")
    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "Password est obligatoire")
    @Size(min = 6, message = "Password doit contenir au moins 6 caractères")
    private String password;

    private String role = "TECHNICIEN";
}

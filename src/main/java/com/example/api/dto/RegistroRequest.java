package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
    @NotBlank @Size(max = 50) String username,
    @NotBlank @Size(min = 6, max = 50) String password,
    @NotBlank @Size(max = 100) String nombre
) {}
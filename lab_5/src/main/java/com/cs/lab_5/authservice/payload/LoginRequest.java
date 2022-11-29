package com.cs.lab_5.authservice.payload;


import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username, @NotBlank String password) {
}

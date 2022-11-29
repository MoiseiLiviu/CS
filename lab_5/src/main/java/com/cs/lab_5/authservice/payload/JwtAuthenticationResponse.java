package com.cs.lab_5.authservice.payload;


import javax.validation.constraints.NotNull;

public record JwtAuthenticationResponse(@NotNull String accessToken, boolean mfaVerified) {

}

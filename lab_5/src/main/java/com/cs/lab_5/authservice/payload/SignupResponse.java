package com.cs.lab_5.authservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {

    private boolean mfa;
    private String secretImageUri;
}
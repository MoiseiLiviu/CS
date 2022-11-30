package com.cs.lab_5.authservice.payload;

public record SignUpRequest(String username, String email, String password, boolean mfa) {
}

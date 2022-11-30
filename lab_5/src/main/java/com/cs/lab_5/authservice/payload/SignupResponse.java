package com.cs.lab_5.authservice.payload;

public record SignupResponse(boolean mfa, String secretImageUri) {

}

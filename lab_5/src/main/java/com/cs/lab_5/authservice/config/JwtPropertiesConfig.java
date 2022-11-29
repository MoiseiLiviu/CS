package com.cs.lab_5.authservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtPropertiesConfig {

    @Value("${security.jwt.header:}")
    private String header;

    @Value("${security.jwt.prefix:}")
    private String prefix;

    @Value("${security.jwt.expiration:1234}")
    private int expiration;

    @Value("${security.jwt.secret:}")
    private String secret;
}

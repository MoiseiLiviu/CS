package com.cs.lab_5.authservice.service;

import com.cs.lab_5.authservice.config.JwtPropertiesConfig;
import com.cs.lab_5.authservice.model.User;
import com.cs.lab_5.authservice.model.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenManager {

    private final JwtPropertiesConfig jwtPropertiesConfig;

    private static final String AUTHORITIES_CLAIM_KEY = "authorities";
    private static final String VERIFIED_AUTHORITY = "VERIFIED";

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        long now = System.currentTimeMillis();
        final String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_CLAIM_KEY, roles)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtPropertiesConfig.getExpiration() * 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtPropertiesConfig.getSecret().getBytes())
                .compact();
    }

    public Claims getClaimsFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtPropertiesConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final User user) {
        final var claims = getClaimsFromJWT(token);
        final var authorities = Arrays.stream(claims.get(AUTHORITIES_CLAIM_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(new UserDetailsImpl(user), "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtPropertiesConfig.getSecret().getBytes())
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String generateVerifiedToken() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var authorities = new ArrayList<>(
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
        authorities.add(new SimpleGrantedAuthority(VERIFIED_AUTHORITY));

        return generateToken(authentication.getName(), authorities);
    }
}

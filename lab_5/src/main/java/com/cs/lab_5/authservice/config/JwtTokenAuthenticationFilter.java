package com.cs.lab_5.authservice.config;

import com.cs.lab_5.authservice.service.JwtTokenManager;
import com.cs.lab_5.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtPropertiesConfig jwtPropertiesConfig;
    private final JwtTokenManager tokenProvider;
    private final UserService userService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(jwtPropertiesConfig.getHeader());
        if (header != null && header.startsWith(jwtPropertiesConfig.getPrefix())) {
            String token = header.replace(jwtPropertiesConfig.getPrefix(), "");
            if (tokenProvider.validateToken(token)) {
                final var claims = tokenProvider.getClaimsFromJWT(token);
                final var username = claims.getSubject();
                final var user = userService.findByUsername(username);
                final var auth = tokenProvider.getAuthenticationToken(token, user);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}

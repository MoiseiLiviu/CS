package com.cs.lab_5.authservice.service;

import com.cs.lab_5.authservice.model.ERole;
import com.cs.lab_5.authservice.model.Role;
import com.cs.lab_5.authservice.model.User;
import com.cs.lab_5.authservice.model.UserDetailsImpl;
import com.cs.lab_5.authservice.payload.JwtAuthenticationResponse;
import com.cs.lab_5.authservice.payload.SignUpRequest;
import com.cs.lab_5.authservice.payload.SignupResponse;
import com.cs.lab_5.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TotpManager totpManager;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Transactional
    public SignupResponse registerNewUser(SignUpRequest signUpRequest) {
        User newUser = new User();
        newUser.setUsername(signUpRequest.username());
        newUser.setEmail(signUpRequest.email());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.password()));
        Role role = roleRepository.findByName(ERole.USER).orElseThrow();
        newUser.getRoles().add(role);
        if (signUpRequest.mfa()) {
            newUser.setMfa(true);
            newUser.setSecret(totpManager.generateSecret());
        }
        User savedUser = userService.save(newUser);

        return new SignupResponse(savedUser.isMfa(),
                totpManager.getUriForImage(savedUser.getSecret()));
    }

    @Transactional(readOnly = true)
    public JwtAuthenticationResponse loginUser(String username, String password) {
        final var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final String token = jwtTokenManager.generateToken(username, authentication.getAuthorities());

        return new JwtAuthenticationResponse(token, false);
    }

    @Transactional(readOnly = true)
    public JwtAuthenticationResponse verify(String code) {
        String username = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        if (!totpManager.verifyCode(code, user.getSecret())) {
            throw new AccessDeniedException("Code is incorrect");
        }
        final String token = jwtTokenManager.generateVerifiedToken();

        return new JwtAuthenticationResponse(token, true);
    }
}

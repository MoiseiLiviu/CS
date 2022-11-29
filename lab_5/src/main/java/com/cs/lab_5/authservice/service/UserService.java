package com.cs.lab_5.authservice.service;

import com.cs.lab_5.authservice.exception.ResourceNotFoundException;
import com.cs.lab_5.authservice.model.User;
import com.cs.lab_5.authservice.model.UserDetailsImpl;
import com.cs.lab_5.authservice.payload.UserResponse;
import com.cs.lab_5.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("username %s", username)));
    }

    public UserResponse getCurrentUserDetails() {
        String username = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = findByUsername(username);

        return new UserResponse(currentUser.getUsername(), currentUser.getEmail(), currentUser.getId());
    }
}

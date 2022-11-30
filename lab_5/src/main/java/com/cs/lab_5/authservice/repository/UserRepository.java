package com.cs.lab_5.authservice.repository;

import com.cs.lab_5.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

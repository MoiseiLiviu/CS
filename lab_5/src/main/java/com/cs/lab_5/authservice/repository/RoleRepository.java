package com.cs.lab_5.authservice.repository;

import com.cs.lab_5.authservice.model.ERole;
import com.cs.lab_5.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}

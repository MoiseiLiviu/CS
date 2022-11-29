package com.cs.lab_5.authservice;

import com.cs.lab_5.authservice.model.ERole;
import com.cs.lab_5.authservice.model.Role;
import com.cs.lab_5.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role userRole = new Role();
        userRole.setName(ERole.USER);
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName(ERole.ADMIN);
        roleRepository.save(adminRole);
    }
}

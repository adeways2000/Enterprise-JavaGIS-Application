package com.adeprogramming.javagis.security.service;

import com.adeprogramming.javagis.security.model.Role;
import com.adeprogramming.javagis.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes default roles in the database on application startup.
 */
@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create default roles if they don't exist
        createRoleIfNotFound("ROLE_USER", "Regular user with basic access");
        createRoleIfNotFound("ROLE_MODERATOR", "Moderator with elevated access");
        createRoleIfNotFound("ROLE_ADMIN", "Administrator with full access");
    }

    private void createRoleIfNotFound(String name, String description) {
        if (!roleRepository.existsByName(name)) {
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
        }
    }
}

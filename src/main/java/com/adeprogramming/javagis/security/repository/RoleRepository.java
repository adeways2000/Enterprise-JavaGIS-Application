package com.adeprogramming.javagis.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

/**
 * Repository interface for Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by name.
     *
     * @param name the role name
     * @return an Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role exists with the given name.
     *
     * @param name the role name
     * @return true if a role exists with the given name, false otherwise
     */
    Boolean existsByName(String name);
}
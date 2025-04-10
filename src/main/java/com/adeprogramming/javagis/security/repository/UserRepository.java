package com.adeprogramming.javagis.security.repository;

import com.adeprogramming.javagis.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     *
     * @param username the username
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by email.
     *
     * @param email the email
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists with the given username.
     *
     * @param username the username
     * @return true if a user exists with the given username, false otherwise
     */
    Boolean existsByUsername(String username);

    /**
     * Check if a user exists with the given email.
     *
     * @param email the email
     * @return true if a user exists with the given email, false otherwise
     */
    Boolean existsByEmail(String email);
}

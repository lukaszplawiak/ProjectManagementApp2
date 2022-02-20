package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    List<User> findAll();
    User save(User user);
    void deleteUserByEmail(String email);

    default User getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found in database"));
    }
}
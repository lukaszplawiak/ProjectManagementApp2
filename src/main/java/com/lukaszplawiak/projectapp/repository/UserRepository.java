package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

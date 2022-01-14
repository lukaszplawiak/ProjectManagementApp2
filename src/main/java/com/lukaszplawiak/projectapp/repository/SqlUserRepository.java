package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlUserRepository extends UserRepository, JpaRepository<User, Long> {
    @Override
    void deleteUserByEmail(String email);
}

package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

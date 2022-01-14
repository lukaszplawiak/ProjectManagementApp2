package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository {
    Role findByName(String name);

    List<Role> findAll();

    Role save(Role role);
}

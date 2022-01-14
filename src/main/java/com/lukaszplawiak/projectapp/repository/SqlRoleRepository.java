package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlRoleRepository extends RoleRepository, JpaRepository<Role, Long> {
}

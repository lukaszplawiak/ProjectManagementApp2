package com.lukaszplawiak.projectapp.security;

public class RoleAndUserForm {
    private String email;
    private String roleName;

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

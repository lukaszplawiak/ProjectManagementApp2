package com.lukaszplawiak.projectapp.model;

public class RoleAndUserForm {
    private String email;
    private String roleName;

    public RoleAndUserForm(String email, String roleName) {
        this.email = email;
        this.roleName = roleName;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }
}
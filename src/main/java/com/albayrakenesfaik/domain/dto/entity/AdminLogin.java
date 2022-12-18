package com.albayrakenesfaik.domain.dto.entity;

public class AdminLogin {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public AdminLogin setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdminLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "AdminLogin{" +
            "email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}

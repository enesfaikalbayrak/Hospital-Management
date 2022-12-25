package com.albayrakenesfaik.domain.dto;

import com.albayrakenesfaik.domain.dto.enumaration.UserType;

public class UserLoginDTO {
    private String identityNumber;
    private String password;
    private UserType userType;

    public String getIdentityNumber() {
        return identityNumber;
    }

    public UserLoginDTO setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserLoginDTO setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
            "identityNumber='" + identityNumber + '\'' +
            ", password='" + password + '\'' +
            ", userType=" + userType +
            '}';
    }
}

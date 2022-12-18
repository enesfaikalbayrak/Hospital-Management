package com.albayrakenesfaik.domain.dto;

import com.albayrakenesfaik.domain.enumeration.BloodType;
import com.albayrakenesfaik.domain.enumeration.Gender;

public class PatientDTO {
    private String fulName;
    private String telephoneNumber;
    private Gender gender;
    private BloodType bloodType;
    private String identityNumber;

    public String getFulName() {
        return fulName;
    }

    public PatientDTO setFulName(String fulName) {
        this.fulName = fulName;
        return this;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public PatientDTO setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public PatientDTO setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public PatientDTO setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
        return this;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public PatientDTO setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
            "fulName='" + fulName + '\'' +
            ", telephoneNumber='" + telephoneNumber + '\'' +
            ", gender=" + gender +
            ", bloodType=" + bloodType +
            ", identityNumber='" + identityNumber + '\'' +
            '}';
    }
}

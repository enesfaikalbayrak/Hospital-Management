package com.albayrakenesfaik.domain.dto;

import com.albayrakenesfaik.domain.Department;
import com.albayrakenesfaik.domain.enumeration.BloodType;
import com.albayrakenesfaik.domain.enumeration.Gender;

public class DoctorDTO {

    private String fullName;
    private String email;
    private String identityNumber;
    private String telephoneNumber;
    private Gender gender;
    private BloodType bloodType;
    private String specialist;
    private String avaliableTimes;
    private Department department;

    public String getFullName() {
        return fullName;
    }

    public DoctorDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DoctorDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public DoctorDTO setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public DoctorDTO setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public DoctorDTO setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public DoctorDTO setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
        return this;
    }

    public String getSpecialist() {
        return specialist;
    }

    public DoctorDTO setSpecialist(String specialist) {
        this.specialist = specialist;
        return this;
    }

    public String getAvaliableTimes() {
        return avaliableTimes;
    }

    public DoctorDTO setAvaliableTimes(String avaliableTimes) {
        this.avaliableTimes = avaliableTimes;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public DoctorDTO setDepartment(Department department) {
        this.department = department;
        return this;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
            "fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", identityNumber='" + identityNumber + '\'' +
            ", telephoneNumber='" + telephoneNumber + '\'' +
            ", gender=" + gender +
            ", bloodType=" + bloodType +
            ", specialist='" + specialist + '\'' +
            ", avaliableTimes='" + avaliableTimes + '\'' +
            ", department=" + department +
            '}';
    }
}

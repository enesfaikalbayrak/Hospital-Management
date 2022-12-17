package com.albayrakenesfaik.domain;

import com.albayrakenesfaik.domain.enumeration.BloodType;
import com.albayrakenesfaik.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(max = 2023)
    @Column(name = "identity_number", length = 2023, nullable = false)
    private String identityNumber;

    @NotNull
    @Size(max = 2023)
    @Column(name = "telephone_number", length = 2023, nullable = false)
    private String telephoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "doctor" }, allowSetters = true)
    private Set<Appoitment> appoitments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Patient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Patient fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public Patient email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNumber() {
        return this.identityNumber;
    }

    public Patient identityNumber(String identityNumber) {
        this.setIdentityNumber(identityNumber);
        return this;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public Patient telephoneNumber(String telephoneNumber) {
        this.setTelephoneNumber(telephoneNumber);
        return this;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Patient gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return this.password;
    }

    public Patient password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

    public Patient bloodType(BloodType bloodType) {
        this.setBloodType(bloodType);
        return this;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Set<Appoitment> getAppoitments() {
        return this.appoitments;
    }

    public void setAppoitments(Set<Appoitment> appoitments) {
        if (this.appoitments != null) {
            this.appoitments.forEach(i -> i.setPatient(null));
        }
        if (appoitments != null) {
            appoitments.forEach(i -> i.setPatient(this));
        }
        this.appoitments = appoitments;
    }

    public Patient appoitments(Set<Appoitment> appoitments) {
        this.setAppoitments(appoitments);
        return this;
    }

    public Patient addAppoitment(Appoitment appoitment) {
        this.appoitments.add(appoitment);
        appoitment.setPatient(this);
        return this;
    }

    public Patient removeAppoitment(Appoitment appoitment) {
        this.appoitments.remove(appoitment);
        appoitment.setPatient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", identityNumber='" + getIdentityNumber() + "'" +
            ", telephoneNumber='" + getTelephoneNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", password='" + getPassword() + "'" +
            ", bloodType='" + getBloodType() + "'" +
            "}";
    }
}

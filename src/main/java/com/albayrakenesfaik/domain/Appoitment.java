package com.albayrakenesfaik.domain;

import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Appoitment.
 */
@Entity
@Table(name = "appoitment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Appoitment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "patient_information")
    private String patientInformation;

    @Lob
    @Column(name = "doctor_information")
    private String doctorInformation;

    @Lob
    @Column(name = "prescription")
    private String prescription;

    @Column(name = "date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "appoitment_status")
    private AppoitmentStatus appoitmentStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "appoitments" }, allowSetters = true)
    private Patient patient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "appoitments", "department" }, allowSetters = true)
    private Doctor doctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appoitment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientInformation() {
        return this.patientInformation;
    }

    public Appoitment patientInformation(String patientInformation) {
        this.setPatientInformation(patientInformation);
        return this;
    }

    public void setPatientInformation(String patientInformation) {
        this.patientInformation = patientInformation;
    }

    public String getDoctorInformation() {
        return this.doctorInformation;
    }

    public Appoitment doctorInformation(String doctorInformation) {
        this.setDoctorInformation(doctorInformation);
        return this;
    }

    public void setDoctorInformation(String doctorInformation) {
        this.doctorInformation = doctorInformation;
    }

    public String getPrescription() {
        return this.prescription;
    }

    public Appoitment prescription(String prescription) {
        this.setPrescription(prescription);
        return this;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Instant getDate() {
        return this.date;
    }

    public Appoitment date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public AppoitmentStatus getAppoitmentStatus() {
        return this.appoitmentStatus;
    }

    public Appoitment appoitmentStatus(AppoitmentStatus appoitmentStatus) {
        this.setAppoitmentStatus(appoitmentStatus);
        return this;
    }

    public void setAppoitmentStatus(AppoitmentStatus appoitmentStatus) {
        this.appoitmentStatus = appoitmentStatus;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appoitment patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Appoitment doctor(Doctor doctor) {
        this.setDoctor(doctor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appoitment)) {
            return false;
        }
        return id != null && id.equals(((Appoitment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appoitment{" +
            "id=" + getId() +
            ", patientInformation='" + getPatientInformation() + "'" +
            ", doctorInformation='" + getDoctorInformation() + "'" +
            ", prescription='" + getPrescription() + "'" +
            ", date='" + getDate() + "'" +
            ", appoitmentStatus='" + getAppoitmentStatus() + "'" +
            "}";
    }
}

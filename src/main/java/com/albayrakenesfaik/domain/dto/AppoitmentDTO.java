package com.albayrakenesfaik.domain.dto;

import com.albayrakenesfaik.domain.dto.entity.PrescriptionDTO;
import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;

import java.time.Instant;

public class AppoitmentDTO {
    private Long id;
    private PatientDTO patientDTO;
    private DoctorDTO doctorDTO;
    private PrescriptionDTO prescriptionDTO;
    private Instant date;
    private AppoitmentStatus appoitmentStatus;

    public Long getId() {
        return id;
    }

    public AppoitmentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public PatientDTO getPatientDTO() {
        return patientDTO;
    }

    public AppoitmentDTO setPatientDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
        return this;
    }

    public DoctorDTO getDoctorDTO() {
        return doctorDTO;
    }

    public AppoitmentDTO setDoctorDTO(DoctorDTO doctorDTO) {
        this.doctorDTO = doctorDTO;
        return this;
    }

    public PrescriptionDTO getPrescriptionDTO() {
        return prescriptionDTO;
    }

    public AppoitmentDTO setPrescriptionDTO(PrescriptionDTO prescriptionDTO) {
        this.prescriptionDTO = prescriptionDTO;
        return this;
    }

    public Instant getDate() {
        return date;
    }

    public AppoitmentDTO setDate(Instant date) {
        this.date = date;
        return this;
    }

    public AppoitmentStatus getAppoitmentStatus() {
        return appoitmentStatus;
    }

    public AppoitmentDTO setAppoitmentStatus(AppoitmentStatus appoitmentStatus) {
        this.appoitmentStatus = appoitmentStatus;
        return this;
    }

    @Override
    public String toString() {
        return "AppoitmentDTO{" +
            "id=" + id +
            ", patientDTO=" + patientDTO +
            ", doctorDTO=" + doctorDTO +
            ", prescriptionDTO=" + prescriptionDTO +
            ", date=" + date +
            ", appoitmentStatus=" + appoitmentStatus +
            '}';
    }
}

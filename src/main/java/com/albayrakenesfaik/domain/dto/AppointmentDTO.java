package com.albayrakenesfaik.domain.dto;

import com.albayrakenesfaik.domain.dto.entity.PrescriptionDTO;
import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;

import java.time.Instant;

public class AppointmentDTO {

    private PatientDTO patientDTO;
    private DoctorDTO doctorDTO;
    private PrescriptionDTO prescriptionDTO;
    private Instant date;
    private AppoitmentStatus appoitmentStatus;



}

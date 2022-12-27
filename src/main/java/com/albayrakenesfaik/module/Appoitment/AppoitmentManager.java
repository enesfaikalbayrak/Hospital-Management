package com.albayrakenesfaik.module.Appoitment;

import com.albayrakenesfaik.domain.dto.AppoitmentDTO;
import com.albayrakenesfaik.extension.definition.Definition;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AppoitmentManager {
    AppoitmentDTO createAppoitment(Definition<AppoitmentDTO> definition);

    AppoitmentDTO updateAppoitment(Definition<AppoitmentDTO> definition);

    Optional<List<AppoitmentDTO>> getAllAppoitments(Instant startTime, Instant endTime);

    Optional<List<AppoitmentDTO>> getAllAppoitmentsByDoctorId(Long doctorId, Instant startTime, Instant endTime);

    Optional<List<AppoitmentDTO>> getAllAppoitmentsByPatientId(Long patientId, Instant startTime, Instant endTime);

}

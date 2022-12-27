package com.albayrakenesfaik.module.Patient;

import com.albayrakenesfaik.domain.dto.PatientDTO;
import com.albayrakenesfaik.extension.definition.Definition;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PatientManager {
    PatientDTO createPatient(Definition<PatientDTO> definition);

    PatientDTO updatePatient(Definition<PatientDTO> definition);

    Optional<List<PatientDTO>> getAllPatients();

    PatientDTO loginPatient(String email, String password);
}

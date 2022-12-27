package com.albayrakenesfaik.web.rest;

import com.albayrakenesfaik.domain.dto.PatientDTO;
import com.albayrakenesfaik.extension.definition.Definition;
import com.albayrakenesfaik.module.Patient.PatientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PatientManagerResource {

    private final Logger log = LoggerFactory.getLogger(PatientManagerResource.class);

    private static final String ENTITY_NAME = "HospitalManagementPatient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientManager patientManager;

    public PatientManagerResource(PatientManager patientManager) {
        this.patientManager = patientManager;
    }

    @PostMapping("/patient-create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        log.debug("REST request to create Patient patient: {}",
            patientDTO);

        PatientDTO createdPatientDTO = this.patientManager.createPatient(Definition.of(patientDTO).build());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, createdPatientDTO.toString()))
            .body(createdPatientDTO);
    }

    @PutMapping("/patient-update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) {
        log.debug("REST request to update Patient : {}", patientDTO);

        PatientDTO updatedPatientDTO = this.patientManager.updatePatient(Definition.of(patientDTO).build());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, updatedPatientDTO.toString()))
            .body(updatedPatientDTO);
    }

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        log.debug("REST request to get all Patients");

        Optional<List<PatientDTO>> patientDTOS = this.patientManager.getAllPatients();

        return ResponseUtil.wrapOrNotFound(patientDTOS);
    }

    @GetMapping("/patient-login")
    public ResponseEntity<PatientDTO> loginDoctor(@RequestParam String email,
                                                  @RequestParam String password) {
        log.debug("REST request to login Patient by email: {}, password: {}",
            email, password);

        PatientDTO loginedPatientDTO = this.patientManager.loginPatient(email, password);

        return ResponseEntity.ok()
            .body(loginedPatientDTO);
    }
}

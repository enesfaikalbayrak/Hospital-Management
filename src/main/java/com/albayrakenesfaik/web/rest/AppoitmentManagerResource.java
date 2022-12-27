package com.albayrakenesfaik.web.rest;

import com.albayrakenesfaik.domain.dto.AppoitmentDTO;
import com.albayrakenesfaik.extension.definition.Definition;
import com.albayrakenesfaik.module.Appoitment.AppoitmentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AppoitmentManagerResource {

    private final Logger log = LoggerFactory.getLogger(AppoitmentManagerResource.class);

    private static final String ENTITY_NAME = "HospitalManagementAppoitment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppoitmentManager appoitmentManager;

    public AppoitmentManagerResource(AppoitmentManager appoitmentManager) {
        this.appoitmentManager = appoitmentManager;
    }

    @PostMapping("/appoitment-create")
    public ResponseEntity<AppoitmentDTO> createAppoitment(@RequestBody AppoitmentDTO appoitmentDTO) {
        log.debug("REST request to create Appoitment appoitment: {}",
            appoitmentDTO);

        AppoitmentDTO createdAppoitmentDTO = this.appoitmentManager.createAppoitment(Definition.of(appoitmentDTO).build());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, createdAppoitmentDTO.toString()))
            .body(createdAppoitmentDTO);
    }

    @PutMapping("/appoitment")
    public ResponseEntity<AppoitmentDTO> updateAppoitment(@RequestBody AppoitmentDTO appoitmentDTO) {
        log.debug("REST request to update Appiotment : {}", appoitmentDTO);

        AppoitmentDTO updatedAppoitmentDTO = this.appoitmentManager.updateAppoitment(Definition.of(appoitmentDTO).build());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, updatedAppoitmentDTO.toString()))
            .body(updatedAppoitmentDTO);
    }

    @GetMapping("/getAppoitments")
    public ResponseEntity<List<AppoitmentDTO>> getAllAppoitments(@RequestParam Instant startTime,
                                                                 @RequestParam Instant endTime) {
        log.debug("REST request to get all Appoitments with startTime : {}, endTime : {}",
            startTime, endTime);

        Optional<List<AppoitmentDTO>> appoitmentDTOS = this.appoitmentManager.getAllAppoitments(startTime, endTime);

        return ResponseUtil.wrapOrNotFound(appoitmentDTOS);
    }

    @GetMapping("/appoitments-doctor")
    public ResponseEntity<List<AppoitmentDTO>> getAllAppoitmentsByDoctorId(@RequestParam Long doctorId,
                                                                           @RequestParam Instant startTime,
                                                                           @RequestParam Instant endTime) {
        log.debug("REST request to get all Appoitments by Doctor with doctorId : {},  startTime : {}, endTime : {}",
            doctorId, startTime, endTime);

        Optional<List<AppoitmentDTO>> appoitmentDTOS = this.appoitmentManager.getAllAppoitmentsByDoctorId(doctorId, startTime, endTime);

        return ResponseUtil.wrapOrNotFound(appoitmentDTOS);
    }

    @GetMapping("/appoitments-patient")
    public ResponseEntity<List<AppoitmentDTO>> getAllAppoitmentsByPatientId(@RequestParam Long patientId,
                                                                            @RequestParam Instant startTime,
                                                                            @RequestParam Instant endTime) {
        log.debug("REST request to get all Appoitments by Patient with patientId : {} ,  startTime : {}, endTime : {}",
            patientId, startTime, endTime);

        Optional<List<AppoitmentDTO>> appoitmentDTOS = this.appoitmentManager.getAllAppoitmentsByPatientId(patientId, startTime, endTime);

        return ResponseUtil.wrapOrNotFound(appoitmentDTOS);
    }
}

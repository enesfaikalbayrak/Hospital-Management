package com.albayrakenesfaik.module.Patient.impl;

import com.albayrakenesfaik.domain.Patient;
import com.albayrakenesfaik.domain.dto.PatientDTO;
import com.albayrakenesfaik.extension.Mapper.HospitalManagementContextMapper;
import com.albayrakenesfaik.extension.definition.Definition;
import com.albayrakenesfaik.extension.validation.Assert;
import com.albayrakenesfaik.extension.validation.BusinessExceptionKey;
import com.albayrakenesfaik.module.Patient.PatientManager;
import com.albayrakenesfaik.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientManagerImpl implements PatientManager {
    private final Logger log = LoggerFactory.getLogger(PatientManagerImpl.class);
    private final PatientRepository patientRepository;
    private final HospitalManagementContextMapper hospitalManagementContextMapper;

    public PatientManagerImpl(PatientRepository patientRepository, HospitalManagementContextMapper hospitalManagementContextMapper) {
        this.patientRepository = patientRepository;
        this.hospitalManagementContextMapper = hospitalManagementContextMapper;
    }

    @Override
    public PatientDTO createPatient(Definition<PatientDTO> definition) {
        PatientDTO patientDTO = definition.getItem();
        log.debug("Creating Patient is requested with Inbox : {}", patientDTO);

        Assert.isTrue(this.isPatientDTOValid(patientDTO), BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        Optional<Patient> patient = patientRepository.findPatientByEmail(patientDTO.getEmail());

        Assert.isTrue(patient.isPresent(), BusinessExceptionKey.PATIENT_ALREADY_EXIST);

        this.patientRepository.save(this.hospitalManagementContextMapper.patientDTOToPatient(patientDTO));

        return patientDTO;
    }

    @Override
    public PatientDTO updatePatient(Definition<PatientDTO> definition) {
        PatientDTO patientDTO = definition.getItem();
        log.debug("Updating patient is requested with patient : {}", patientDTO);

        Assert.isTrue(this.isPatientDTOValid(patientDTO), BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(patientDTO.getId(), BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        Optional<Patient> patient = this.getByPatientId(Definition.get(patientDTO.getId()).build());
        Assert.isTrue(patient.isPresent(), BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        log.debug("The Patient has been obtained from database as : {}", patient);

        Patient updatingPatient = this.hospitalManagementContextMapper.patientDTOToPatient(patientDTO);

        log.debug("The Patient to be updated has been obtained as : {}", patientDTO);
        this.patientRepository.save(updatingPatient);

        return patientDTO;
    }

    @Override
    public Optional<List<PatientDTO>> getAllPatients() {
        List<Patient> patients = this.patientRepository.findAll();

        log.debug("Patients are get as : {}", patients);

        List<PatientDTO> patientDTOS = new ArrayList<>();
        patients.forEach(patient -> patientDTOS.add(this.hospitalManagementContextMapper.patientTOPatientDTO(patient)));

        return Optional.of(patientDTOS);
    }

    @Override
    public PatientDTO loginPatient(String email, String password) {

        log.debug("Login Patient is requested by Patient email: {}, password: {} ", email, password);

        Assert.isNotNull(email, BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(password, BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        Optional<Patient> patient = this.patientRepository.findPatientByEmailAndPassword(email, password);

        Assert.isFalse(patient.isPresent(), BusinessExceptionKey.PATIENT_NOT_EXIST);
        log.debug("Patient are get as : {}", patient);

        PatientDTO patientDTO = this.hospitalManagementContextMapper.patientTOPatientDTO(patient.get());

        return patientDTO;
    }

    public Optional<Patient> getByPatientId(Definition<Void> definition) {
        log.debug("Getting Patient is requested with id : {}", definition.getId());

        return this.patientRepository.findById(definition.getId());
    }

    private boolean isPatientDTOValid(PatientDTO patientDTO) {
        if (patientDTO == null) {
            log.error("Patient is null.");
            return false;
        }
        if (patientDTO.getId() == null) {
            log.error("Id in Patient is null.");
            return false;
        }
        if (patientDTO.getEmail() == null) {
            log.error("Patient email is null.");
            return false;
        }

        if (patientDTO.getIdentityNumber() == null) {
            log.error("Identity Number in Patient is null.");
            return false;
        }

        if (patientDTO.getBloodType() == null) {
            log.error("Blood Type in Patient is null.");
            return false;
        }

        if (patientDTO.getGender() == null) {
            log.error("Gender in Patient is null.");
            return false;
        }
        if (patientDTO.getFulName() == null) {
            log.error("Full name in Patient is null.");
            return false;
        }

        if (patientDTO.getTelephoneNumber() == null) {
            log.error("Telephone number  in Patient is null.");
            return false;
        }

        return true;
    }
}

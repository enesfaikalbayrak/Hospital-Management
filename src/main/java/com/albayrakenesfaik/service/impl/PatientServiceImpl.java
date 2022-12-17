package com.albayrakenesfaik.service.impl;

import com.albayrakenesfaik.domain.Patient;
import com.albayrakenesfaik.repository.PatientRepository;
import com.albayrakenesfaik.service.PatientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        log.debug("Request to update Patient : {}", patient);
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> partialUpdate(Patient patient) {
        log.debug("Request to partially update Patient : {}", patient);

        return patientRepository
            .findById(patient.getId())
            .map(existingPatient -> {
                if (patient.getFullName() != null) {
                    existingPatient.setFullName(patient.getFullName());
                }
                if (patient.getEmail() != null) {
                    existingPatient.setEmail(patient.getEmail());
                }
                if (patient.getIdentityNumber() != null) {
                    existingPatient.setIdentityNumber(patient.getIdentityNumber());
                }
                if (patient.getTelephoneNumber() != null) {
                    existingPatient.setTelephoneNumber(patient.getTelephoneNumber());
                }
                if (patient.getGender() != null) {
                    existingPatient.setGender(patient.getGender());
                }
                if (patient.getPassword() != null) {
                    existingPatient.setPassword(patient.getPassword());
                }
                if (patient.getBloodType() != null) {
                    existingPatient.setBloodType(patient.getBloodType());
                }

                return existingPatient;
            })
            .map(patientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Patient> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}

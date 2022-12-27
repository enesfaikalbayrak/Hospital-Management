package com.albayrakenesfaik.repository;

import com.albayrakenesfaik.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    Optional<Patient> findPatientByEmailAndPassword(String email, String password);

    Optional<Patient> findPatientByEmail(String email);

}

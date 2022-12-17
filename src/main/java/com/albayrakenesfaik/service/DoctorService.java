package com.albayrakenesfaik.service;

import com.albayrakenesfaik.domain.Doctor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Doctor}.
 */
public interface DoctorService {
    /**
     * Save a doctor.
     *
     * @param doctor the entity to save.
     * @return the persisted entity.
     */
    Doctor save(Doctor doctor);

    /**
     * Updates a doctor.
     *
     * @param doctor the entity to update.
     * @return the persisted entity.
     */
    Doctor update(Doctor doctor);

    /**
     * Partially updates a doctor.
     *
     * @param doctor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Doctor> partialUpdate(Doctor doctor);

    /**
     * Get all the doctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Doctor> findAll(Pageable pageable);

    /**
     * Get the "id" doctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Doctor> findOne(Long id);

    /**
     * Delete the "id" doctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

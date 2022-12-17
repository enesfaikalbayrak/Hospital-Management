package com.albayrakenesfaik.service;

import com.albayrakenesfaik.domain.Prescription;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Prescription}.
 */
public interface PrescriptionService {
    /**
     * Save a prescription.
     *
     * @param prescription the entity to save.
     * @return the persisted entity.
     */
    Prescription save(Prescription prescription);

    /**
     * Updates a prescription.
     *
     * @param prescription the entity to update.
     * @return the persisted entity.
     */
    Prescription update(Prescription prescription);

    /**
     * Partially updates a prescription.
     *
     * @param prescription the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Prescription> partialUpdate(Prescription prescription);

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prescription> findAll(Pageable pageable);

    /**
     * Get the "id" prescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prescription> findOne(Long id);

    /**
     * Delete the "id" prescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

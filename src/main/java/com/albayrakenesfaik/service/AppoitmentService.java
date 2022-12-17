package com.albayrakenesfaik.service;

import com.albayrakenesfaik.domain.Appoitment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Appoitment}.
 */
public interface AppoitmentService {
    /**
     * Save a appoitment.
     *
     * @param appoitment the entity to save.
     * @return the persisted entity.
     */
    Appoitment save(Appoitment appoitment);

    /**
     * Updates a appoitment.
     *
     * @param appoitment the entity to update.
     * @return the persisted entity.
     */
    Appoitment update(Appoitment appoitment);

    /**
     * Partially updates a appoitment.
     *
     * @param appoitment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Appoitment> partialUpdate(Appoitment appoitment);

    /**
     * Get all the appoitments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Appoitment> findAll(Pageable pageable);

    /**
     * Get the "id" appoitment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Appoitment> findOne(Long id);

    /**
     * Delete the "id" appoitment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

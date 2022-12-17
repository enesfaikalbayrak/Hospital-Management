package com.albayrakenesfaik.service;

import com.albayrakenesfaik.domain.Admin;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Admin}.
 */
public interface AdminService {
    /**
     * Save a admin.
     *
     * @param admin the entity to save.
     * @return the persisted entity.
     */
    Admin save(Admin admin);

    /**
     * Updates a admin.
     *
     * @param admin the entity to update.
     * @return the persisted entity.
     */
    Admin update(Admin admin);

    /**
     * Partially updates a admin.
     *
     * @param admin the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Admin> partialUpdate(Admin admin);

    /**
     * Get all the admins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Admin> findAll(Pageable pageable);

    /**
     * Get the "id" admin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Admin> findOne(Long id);

    /**
     * Delete the "id" admin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

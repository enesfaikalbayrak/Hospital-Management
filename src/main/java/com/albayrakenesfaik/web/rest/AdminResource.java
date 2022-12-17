package com.albayrakenesfaik.web.rest;

import com.albayrakenesfaik.domain.Admin;
import com.albayrakenesfaik.repository.AdminRepository;
import com.albayrakenesfaik.service.AdminQueryService;
import com.albayrakenesfaik.service.AdminService;
import com.albayrakenesfaik.service.criteria.AdminCriteria;
import com.albayrakenesfaik.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.albayrakenesfaik.domain.Admin}.
 */
@RestController
@RequestMapping("/api")
public class AdminResource {

    private final Logger log = LoggerFactory.getLogger(AdminResource.class);

    private static final String ENTITY_NAME = "hospitalManagementAdmin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminService adminService;

    private final AdminRepository adminRepository;

    private final AdminQueryService adminQueryService;

    public AdminResource(AdminService adminService, AdminRepository adminRepository, AdminQueryService adminQueryService) {
        this.adminService = adminService;
        this.adminRepository = adminRepository;
        this.adminQueryService = adminQueryService;
    }

    /**
     * {@code POST  /admins} : Create a new admin.
     *
     * @param admin the admin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new admin, or with status {@code 400 (Bad Request)} if the admin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admins")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) throws URISyntaxException {
        log.debug("REST request to save Admin : {}", admin);
        if (admin.getId() != null) {
            throw new BadRequestAlertException("A new admin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Admin result = adminService.save(admin);
        return ResponseEntity
            .created(new URI("/api/admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admins/:id} : Updates an existing admin.
     *
     * @param id the id of the admin to save.
     * @param admin the admin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated admin,
     * or with status {@code 400 (Bad Request)} if the admin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the admin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admins/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Admin admin)
        throws URISyntaxException {
        log.debug("REST request to update Admin : {}, {}", id, admin);
        if (admin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, admin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Admin result = adminService.update(admin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, admin.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admins/:id} : Partial updates given fields of an existing admin, field will ignore if it is null
     *
     * @param id the id of the admin to save.
     * @param admin the admin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated admin,
     * or with status {@code 400 (Bad Request)} if the admin is not valid,
     * or with status {@code 404 (Not Found)} if the admin is not found,
     * or with status {@code 500 (Internal Server Error)} if the admin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Admin> partialUpdateAdmin(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Admin admin
    ) throws URISyntaxException {
        log.debug("REST request to partial update Admin partially : {}, {}", id, admin);
        if (admin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, admin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Admin> result = adminService.partialUpdate(admin);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, admin.getId().toString())
        );
    }

    /**
     * {@code GET  /admins} : get all the admins.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of admins in body.
     */
    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins(
        AdminCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Admins by criteria: {}", criteria);
        Page<Admin> page = adminQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admins/count} : count all the admins.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/admins/count")
    public ResponseEntity<Long> countAdmins(AdminCriteria criteria) {
        log.debug("REST request to count Admins by criteria: {}", criteria);
        return ResponseEntity.ok().body(adminQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /admins/:id} : get the "id" admin.
     *
     * @param id the id of the admin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the admin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
        log.debug("REST request to get Admin : {}", id);
        Optional<Admin> admin = adminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(admin);
    }

    /**
     * {@code DELETE  /admins/:id} : delete the "id" admin.
     *
     * @param id the id of the admin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        log.debug("REST request to delete Admin : {}", id);
        adminService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

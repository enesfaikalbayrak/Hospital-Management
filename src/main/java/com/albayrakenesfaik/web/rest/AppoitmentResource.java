package com.albayrakenesfaik.web.rest;

import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.repository.AppoitmentRepository;
import com.albayrakenesfaik.service.AppoitmentQueryService;
import com.albayrakenesfaik.service.AppoitmentService;
import com.albayrakenesfaik.service.criteria.AppoitmentCriteria;
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
 * REST controller for managing {@link com.albayrakenesfaik.domain.Appoitment}.
 */
@RestController
@RequestMapping("/api")
public class AppoitmentResource {

    private final Logger log = LoggerFactory.getLogger(AppoitmentResource.class);

    private static final String ENTITY_NAME = "hospitalManagementAppoitment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppoitmentService appoitmentService;

    private final AppoitmentRepository appoitmentRepository;

    private final AppoitmentQueryService appoitmentQueryService;

    public AppoitmentResource(
        AppoitmentService appoitmentService,
        AppoitmentRepository appoitmentRepository,
        AppoitmentQueryService appoitmentQueryService
    ) {
        this.appoitmentService = appoitmentService;
        this.appoitmentRepository = appoitmentRepository;
        this.appoitmentQueryService = appoitmentQueryService;
    }

    /**
     * {@code POST  /appoitments} : Create a new appoitment.
     *
     * @param appoitment the appoitment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appoitment, or with status {@code 400 (Bad Request)} if the appoitment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appoitments")
    public ResponseEntity<Appoitment> createAppoitment(@Valid @RequestBody Appoitment appoitment) throws URISyntaxException {
        log.debug("REST request to save Appoitment : {}", appoitment);
        if (appoitment.getId() != null) {
            throw new BadRequestAlertException("A new appoitment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appoitment result = appoitmentService.save(appoitment);
        return ResponseEntity
            .created(new URI("/api/appoitments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appoitments/:id} : Updates an existing appoitment.
     *
     * @param id the id of the appoitment to save.
     * @param appoitment the appoitment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appoitment,
     * or with status {@code 400 (Bad Request)} if the appoitment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appoitment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appoitments/{id}")
    public ResponseEntity<Appoitment> updateAppoitment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Appoitment appoitment
    ) throws URISyntaxException {
        log.debug("REST request to update Appoitment : {}, {}", id, appoitment);
        if (appoitment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appoitment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appoitmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Appoitment result = appoitmentService.update(appoitment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appoitment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /appoitments/:id} : Partial updates given fields of an existing appoitment, field will ignore if it is null
     *
     * @param id the id of the appoitment to save.
     * @param appoitment the appoitment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appoitment,
     * or with status {@code 400 (Bad Request)} if the appoitment is not valid,
     * or with status {@code 404 (Not Found)} if the appoitment is not found,
     * or with status {@code 500 (Internal Server Error)} if the appoitment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appoitments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appoitment> partialUpdateAppoitment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Appoitment appoitment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Appoitment partially : {}, {}", id, appoitment);
        if (appoitment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appoitment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appoitmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Appoitment> result = appoitmentService.partialUpdate(appoitment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appoitment.getId().toString())
        );
    }

    /**
     * {@code GET  /appoitments} : get all the appoitments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appoitments in body.
     */
    @GetMapping("/appoitments")
    public ResponseEntity<List<Appoitment>> getAllAppoitments(
        AppoitmentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Appoitments by criteria: {}", criteria);
        Page<Appoitment> page = appoitmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appoitments/count} : count all the appoitments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/appoitments/count")
    public ResponseEntity<Long> countAppoitments(AppoitmentCriteria criteria) {
        log.debug("REST request to count Appoitments by criteria: {}", criteria);
        return ResponseEntity.ok().body(appoitmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appoitments/:id} : get the "id" appoitment.
     *
     * @param id the id of the appoitment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appoitment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appoitments/{id}")
    public ResponseEntity<Appoitment> getAppoitment(@PathVariable Long id) {
        log.debug("REST request to get Appoitment : {}", id);
        Optional<Appoitment> appoitment = appoitmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appoitment);
    }

    /**
     * {@code DELETE  /appoitments/:id} : delete the "id" appoitment.
     *
     * @param id the id of the appoitment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appoitments/{id}")
    public ResponseEntity<Void> deleteAppoitment(@PathVariable Long id) {
        log.debug("REST request to delete Appoitment : {}", id);
        appoitmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.albayrakenesfaik.service;

import com.albayrakenesfaik.domain.*; // for static metamodels
import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.repository.AppoitmentRepository;
import com.albayrakenesfaik.service.criteria.AppoitmentCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Appoitment} entities in the database.
 * The main input is a {@link AppoitmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Appoitment} or a {@link Page} of {@link Appoitment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppoitmentQueryService extends QueryService<Appoitment> {

    private final Logger log = LoggerFactory.getLogger(AppoitmentQueryService.class);

    private final AppoitmentRepository appoitmentRepository;

    public AppoitmentQueryService(AppoitmentRepository appoitmentRepository) {
        this.appoitmentRepository = appoitmentRepository;
    }

    /**
     * Return a {@link List} of {@link Appoitment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Appoitment> findByCriteria(AppoitmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Appoitment> specification = createSpecification(criteria);
        return appoitmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Appoitment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Appoitment> findByCriteria(AppoitmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Appoitment> specification = createSpecification(criteria);
        return appoitmentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppoitmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Appoitment> specification = createSpecification(criteria);
        return appoitmentRepository.count(specification);
    }

    /**
     * Function to convert {@link AppoitmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Appoitment> createSpecification(AppoitmentCriteria criteria) {
        Specification<Appoitment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Appoitment_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Appoitment_.date));
            }
            if (criteria.getAppoitmentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getAppoitmentStatus(), Appoitment_.appoitmentStatus));
            }
            if (criteria.getPatientId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPatientId(), root -> root.join(Appoitment_.patient, JoinType.LEFT).get(Patient_.id))
                    );
            }
            if (criteria.getDoctorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDoctorId(), root -> root.join(Appoitment_.doctor, JoinType.LEFT).get(Doctor_.id))
                    );
            }
        }
        return specification;
    }
}

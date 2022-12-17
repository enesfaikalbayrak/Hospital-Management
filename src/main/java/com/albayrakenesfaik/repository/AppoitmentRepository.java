package com.albayrakenesfaik.repository;

import com.albayrakenesfaik.domain.Appoitment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Appoitment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppoitmentRepository extends JpaRepository<Appoitment, Long>, JpaSpecificationExecutor<Appoitment> {}

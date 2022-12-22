package com.albayrakenesfaik.repository;

import com.albayrakenesfaik.domain.Appoitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the Appoitment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppoitmentRepository extends JpaRepository<Appoitment, Long>, JpaSpecificationExecutor<Appoitment> {
    List<Appoitment> findAllByDateBetween(Instant startTime, Instant endTime);
}

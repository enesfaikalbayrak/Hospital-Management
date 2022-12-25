package com.albayrakenesfaik.repository;

import com.albayrakenesfaik.domain.Appoitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select appoitment from Appoitment appoitment" +
        " where appoitment.doctor.id = :doctorId" +
        " and appoitment.date > :startTime" +
        " and appoitment.date < :endTime")
    List<Appoitment> findAllByDoctorIdAndDateBetween(@Param("doctorId") Long doctorId,
                                                     @Param("startTime") Instant startTime,
                                                     @Param("endTime") Instant endTime);

    @Query("select appoitment from Appoitment appoitment" +
        " where appoitment.patient.id = :patientId" +
        " and appoitment.date > :startTime" +
        " and appoitment.date < :endTime")
    List<Appoitment> findAllAppoitmentsByPatientIdAndDateBetween(@Param("patientId") Long patientId,
                                                                 @Param("startTime") Instant startTime,
                                                                 @Param("endTime") Instant endTime);
}

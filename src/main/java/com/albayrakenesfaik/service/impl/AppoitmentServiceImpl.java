package com.albayrakenesfaik.service.impl;

import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.repository.AppoitmentRepository;
import com.albayrakenesfaik.service.AppoitmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Appoitment}.
 */
@Service
@Transactional
public class AppoitmentServiceImpl implements AppoitmentService {

    private final Logger log = LoggerFactory.getLogger(AppoitmentServiceImpl.class);

    private final AppoitmentRepository appoitmentRepository;

    public AppoitmentServiceImpl(AppoitmentRepository appoitmentRepository) {
        this.appoitmentRepository = appoitmentRepository;
    }

    @Override
    public Appoitment save(Appoitment appoitment) {
        log.debug("Request to save Appoitment : {}", appoitment);
        return appoitmentRepository.save(appoitment);
    }

    @Override
    public Appoitment update(Appoitment appoitment) {
        log.debug("Request to update Appoitment : {}", appoitment);
        return appoitmentRepository.save(appoitment);
    }

    @Override
    public Optional<Appoitment> partialUpdate(Appoitment appoitment) {
        log.debug("Request to partially update Appoitment : {}", appoitment);

        return appoitmentRepository
            .findById(appoitment.getId())
            .map(existingAppoitment -> {
                if (appoitment.getPatientInformation() != null) {
                    existingAppoitment.setPatientInformation(appoitment.getPatientInformation());
                }
                if (appoitment.getDoctorInformation() != null) {
                    existingAppoitment.setDoctorInformation(appoitment.getDoctorInformation());
                }
                if (appoitment.getPrescription() != null) {
                    existingAppoitment.setPrescription(appoitment.getPrescription());
                }
                if (appoitment.getDate() != null) {
                    existingAppoitment.setDate(appoitment.getDate());
                }
                if (appoitment.getAppoitmentStatus() != null) {
                    existingAppoitment.setAppoitmentStatus(appoitment.getAppoitmentStatus());
                }

                return existingAppoitment;
            })
            .map(appoitmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Appoitment> findAll(Pageable pageable) {
        log.debug("Request to get all Appoitments");
        return appoitmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Appoitment> findOne(Long id) {
        log.debug("Request to get Appoitment : {}", id);
        return appoitmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appoitment : {}", id);
        appoitmentRepository.deleteById(id);
    }
}

package com.albayrakenesfaik.service.impl;

import com.albayrakenesfaik.domain.Prescription;
import com.albayrakenesfaik.repository.PrescriptionRepository;
import com.albayrakenesfaik.service.PrescriptionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Prescription}.
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Prescription save(Prescription prescription) {
        log.debug("Request to save Prescription : {}", prescription);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription update(Prescription prescription) {
        log.debug("Request to update Prescription : {}", prescription);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Optional<Prescription> partialUpdate(Prescription prescription) {
        log.debug("Request to partially update Prescription : {}", prescription);

        return prescriptionRepository
            .findById(prescription.getId())
            .map(existingPrescription -> {
                if (prescription.getNote() != null) {
                    existingPrescription.setNote(prescription.getNote());
                }
                if (prescription.getMedicines() != null) {
                    existingPrescription.setMedicines(prescription.getMedicines());
                }

                return existingPrescription;
            })
            .map(prescriptionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prescription> findAll(Pageable pageable) {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prescription> findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
    }
}

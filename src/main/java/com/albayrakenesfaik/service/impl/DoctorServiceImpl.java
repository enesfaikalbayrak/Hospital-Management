package com.albayrakenesfaik.service.impl;

import com.albayrakenesfaik.domain.Doctor;
import com.albayrakenesfaik.repository.DoctorRepository;
import com.albayrakenesfaik.service.DoctorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Doctor}.
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor save(Doctor doctor) {
        log.debug("Request to save Doctor : {}", doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor) {
        log.debug("Request to update Doctor : {}", doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public Optional<Doctor> partialUpdate(Doctor doctor) {
        log.debug("Request to partially update Doctor : {}", doctor);

        return doctorRepository
            .findById(doctor.getId())
            .map(existingDoctor -> {
                if (doctor.getFullName() != null) {
                    existingDoctor.setFullName(doctor.getFullName());
                }
                if (doctor.getEmail() != null) {
                    existingDoctor.setEmail(doctor.getEmail());
                }
                if (doctor.getIdentityNumber() != null) {
                    existingDoctor.setIdentityNumber(doctor.getIdentityNumber());
                }
                if (doctor.getTelephoneNumber() != null) {
                    existingDoctor.setTelephoneNumber(doctor.getTelephoneNumber());
                }
                if (doctor.getGender() != null) {
                    existingDoctor.setGender(doctor.getGender());
                }
                if (doctor.getPassword() != null) {
                    existingDoctor.setPassword(doctor.getPassword());
                }
                if (doctor.getBloodType() != null) {
                    existingDoctor.setBloodType(doctor.getBloodType());
                }
                if (doctor.getSpecialist() != null) {
                    existingDoctor.setSpecialist(doctor.getSpecialist());
                }
                if (doctor.getAvaliableTimes() != null) {
                    existingDoctor.setAvaliableTimes(doctor.getAvaliableTimes());
                }

                return existingDoctor;
            })
            .map(doctorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Doctor> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        return doctorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        return doctorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.deleteById(id);
    }
}

package com.albayrakenesfaik.module.Appoitment.impl;

import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.domain.dto.AppoitmentDTO;
import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;
import com.albayrakenesfaik.extension.Mapper.HospitalManagementContextMapper;
import com.albayrakenesfaik.extension.definition.Definition;
import com.albayrakenesfaik.extension.validation.Assert;
import com.albayrakenesfaik.extension.validation.BusinessException;
import com.albayrakenesfaik.extension.validation.BusinessExceptionKey;
import com.albayrakenesfaik.module.Appoitment.AppoitmentManager;
import com.albayrakenesfaik.repository.AppoitmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppoitmentManagerImpl implements AppoitmentManager {
    private final Logger log = LoggerFactory.getLogger(AppoitmentManagerImpl.class);
    private final ObjectMapper objectMapper;
    private final AppoitmentRepository appoitmentRepository;
    private final HospitalManagementContextMapper hospitalManagementContextMapper;

    public AppoitmentManagerImpl(ObjectMapper objectMapper,
                                 AppoitmentRepository appoitmentRepository,
                                 HospitalManagementContextMapper hospitalManagementContextMapper) {
        this.objectMapper = objectMapper;
        this.appoitmentRepository = appoitmentRepository;
        this.hospitalManagementContextMapper = hospitalManagementContextMapper;
    }

    @Override
    public AppoitmentDTO createAppoitment(Definition<AppoitmentDTO> definition) {
        AppoitmentDTO appoitmentDTO = definition.getItem();
        log.debug("Creating Appoitment is requested with Inbox : {}", appoitmentDTO);

        Assert.isTrue(this.isAppoitmentDTOValid(appoitmentDTO), BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        Appoitment appoitment = this.hospitalManagementContextMapper.appoitmentDTOToAppoitment(appoitmentDTO);

        log.debug("The Appointment to be created has been obtained as : {}", appoitmentDTO);
        this.appoitmentRepository.save(appoitment);

        return appoitmentDTO;
    }

    @Override
    public AppoitmentDTO updateAppoitment(Definition<AppoitmentDTO> definition) {
        AppoitmentDTO appoitmentDTO = definition.getItem();
        log.debug("Updating appoitment is requested with appoitment : {}", appoitmentDTO);

        Assert.isTrue(this.isAppoitmentDTOValid(appoitmentDTO), BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(appoitmentDTO.getId(), BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        Optional<Appoitment> appoitment = this.getAppoitmentById(Definition.get(appoitmentDTO.getId()).build());
        Assert.isTrue(appoitment.isPresent(), BusinessExceptionKey.APPOITMENT_NOT_FOUND);

        log.debug("The Appoitment has been obtained from database as : {}", appoitment);

        Appoitment updatingAppoitment = this.hospitalManagementContextMapper.appoitmentDTOToAppoitment(appoitmentDTO);

        log.debug("The Appoitment to be updated has been obtained as : {}", appoitmentDTO);
        this.appoitmentRepository.save(updatingAppoitment);

        return appoitmentDTO;
    }

    @Override
    public Optional<List<AppoitmentDTO>> getAllAppoitments(Instant startTime,
                                                           Instant endTime) {
        log.debug("Getting all Appoitment are requested by  startTime : {} and endTime : {} ",
            startTime, endTime);

        Assert.isNotNull(startTime, BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(endTime, BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        List<Appoitment> appoitments = this.appoitmentRepository.findAllByDateBetween(startTime, endTime);

        log.debug("Appoitments are get as : {}", appoitments);

        List<AppoitmentDTO> appoitmentDTOS = new ArrayList<>();
        appoitments.forEach(appoitment -> appoitmentDTOS.add(this.hospitalManagementContextMapper.appoitmentTOAppoinmentDTO(appoitment)));

        return Optional.of(appoitmentDTOS);
    }

    @Override
    public Optional<List<AppoitmentDTO>> getAllAppoitmentsByDoctorId(Long doctorId, Instant startTime, Instant endTime) {
        log.debug("Getting all Appoitment by doctor Id are requested by doctorId : {} startTime : {} and endTime : {} ",
            doctorId, startTime, endTime);

        Assert.isNotNull(doctorId, BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(startTime, BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(endTime, BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        List<Appoitment> appoitments = this.appoitmentRepository.findAllByDoctorIdAndDateBetween(doctorId, startTime, endTime);

        log.debug("Appoitments are get as : {}", appoitments);

        List<AppoitmentDTO> appoitmentDTOS = new ArrayList<>();
        appoitments.forEach(appoitment -> appoitmentDTOS.add(this.hospitalManagementContextMapper.appoitmentTOAppoinmentDTO(appoitment)));

        return Optional.of(appoitmentDTOS);
    }

    @Override
    public Optional<List<AppoitmentDTO>> getAllAppoitmentsByPatientId(Long patientId, Instant startTime, Instant endTime) {
        log.debug("Getting all Appoitment by patient Id are requested by doctorId : {} startTime : {} and endTime : {} ",
            patientId, startTime, endTime);

        Assert.isNotNull(patientId, BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(startTime, BusinessExceptionKey.REQUEST_PARAMS_MISSING);
        Assert.isNotNull(endTime, BusinessExceptionKey.REQUEST_PARAMS_MISSING);

        List<Appoitment> appoitments = this.appoitmentRepository.findAllAppoitmentsByPatientIdAndDateBetween(patientId, startTime, endTime);

        log.debug("Appoitments are get as : {}", appoitments);

        List<AppoitmentDTO> appoitmentDTOS = new ArrayList<>();
        appoitments.forEach(appoitment -> appoitmentDTOS.add(this.hospitalManagementContextMapper.appoitmentTOAppoinmentDTO(appoitment)));

        return Optional.of(appoitmentDTOS);
    }

    private Optional<Appoitment> getAppoitmentById(Definition<Void> definition) {
        log.debug("Getting Appoitment is requested with id : {}", definition.getId());

        return this.appoitmentRepository.findById(definition.getId());
    }

    private boolean isAppoitmentDTOValid(AppoitmentDTO appoitmentDTO) {
        if (appoitmentDTO == null) {
            log.error("Appoitment is null.");
            return false;
        }

        if (appoitmentDTO.getPatientDTO() == null) {
            log.error("Patient in Appoitment is null.");
            return false;
        }

        if (appoitmentDTO.getDoctorDTO() == null) {
            log.error("Doctor in Appoitment is null.");
            return false;
        }

        if (appoitmentDTO.getDate() == null) {
            log.error("Date in Appoitment is null.");
            return false;
        }

        return true;
    }
}

package com.albayrakenesfaik.extension.Mapper;


import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.domain.dto.AppoitmentDTO;
import com.albayrakenesfaik.domain.dto.DoctorDTO;
import com.albayrakenesfaik.domain.dto.PatientDTO;
import com.albayrakenesfaik.domain.dto.entity.PrescriptionDTO;
import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;
import com.albayrakenesfaik.extension.validation.BusinessException;
import com.albayrakenesfaik.extension.validation.BusinessExceptionKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HospitalManagementContextMapper {
    private final Logger log = LoggerFactory.getLogger(HospitalManagementContextMapper.class);

    private final ObjectMapper objectMapper;


    public HospitalManagementContextMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }


    public Appoitment appoitmentDTOToAppoitment(AppoitmentDTO appoitmentDTO) {
        Appoitment appoitment = new Appoitment();

        try {
            String doctorInformation = objectMapper.writeValueAsString(appoitmentDTO.getDoctorDTO());
            String patientInformation = objectMapper.writeValueAsString(appoitmentDTO.getPatientDTO());
            String prescriptionInformation = objectMapper.writeValueAsString(appoitmentDTO.getPrescriptionDTO());
            AppoitmentStatus appoitmentStatus = appoitmentDTO.getAppoitmentStatus();

            appoitment.setPatientInformation(patientInformation);
            appoitment.setDoctorInformation(doctorInformation);
            appoitment.setPrescription(prescriptionInformation);
            appoitment.setAppoitmentStatus(appoitmentStatus);

        } catch (Exception exception) {
            log.error("An unhandled error occurred in create Appoitment when convert json to appointmentDTO : {} ",
                appoitmentDTO);
            throw new BusinessException(BusinessExceptionKey.JSON_PARSE_ERROR);
        }

        return appoitment;
    }

    public AppoitmentDTO appoitmentTOAppoinmentDTO(Appoitment appoitment) {
        AppoitmentDTO appoitmentDTO = new AppoitmentDTO();

        try {

            DoctorDTO doctorDTO = this.objectMapper.readValue(appoitment.getDoctorInformation(), DoctorDTO.class);
            PatientDTO patientDTO = this.objectMapper.readValue(appoitment.getPatientInformation(), PatientDTO.class);
            PrescriptionDTO prescriptionDTO = this.objectMapper.readValue(appoitment.getPrescription(), PrescriptionDTO.class);

            appoitmentDTO.setId(appoitment.getId());
            appoitmentDTO.setDoctorDTO(doctorDTO);
            appoitmentDTO.setPatientDTO(patientDTO);
            appoitmentDTO.setPrescriptionDTO(prescriptionDTO);
            appoitmentDTO.setAppoitmentStatus(appoitment.getAppoitmentStatus());
            appoitmentDTO.setDate(appoitment.getDate());

        } catch (Exception exception) {
            log.error("An unhandled error occurred in appoitmentTOAppoinmentDTO when convert json to appoitment : {}", appoitment, exception);
        }

        return appoitmentDTO;
    }


}

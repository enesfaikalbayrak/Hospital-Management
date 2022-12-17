package com.albayrakenesfaik.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.albayrakenesfaik.IntegrationTest;
import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.domain.Doctor;
import com.albayrakenesfaik.domain.Patient;
import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;
import com.albayrakenesfaik.repository.AppoitmentRepository;
import com.albayrakenesfaik.service.criteria.AppoitmentCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AppoitmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppoitmentResourceIT {

    private static final String DEFAULT_PATIENT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_PRESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AppoitmentStatus DEFAULT_APPOITMENT_STATUS = AppoitmentStatus.WAITING;
    private static final AppoitmentStatus UPDATED_APPOITMENT_STATUS = AppoitmentStatus.PROCESSED;

    private static final String ENTITY_API_URL = "/api/appoitments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppoitmentRepository appoitmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppoitmentMockMvc;

    private Appoitment appoitment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appoitment createEntity(EntityManager em) {
        Appoitment appoitment = new Appoitment()
            .patientInformation(DEFAULT_PATIENT_INFORMATION)
            .doctorInformation(DEFAULT_DOCTOR_INFORMATION)
            .prescription(DEFAULT_PRESCRIPTION)
            .date(DEFAULT_DATE)
            .appoitmentStatus(DEFAULT_APPOITMENT_STATUS);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        appoitment.setPatient(patient);
        // Add required entity
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            doctor = DoctorResourceIT.createEntity(em);
            em.persist(doctor);
            em.flush();
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        appoitment.setDoctor(doctor);
        return appoitment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appoitment createUpdatedEntity(EntityManager em) {
        Appoitment appoitment = new Appoitment()
            .patientInformation(UPDATED_PATIENT_INFORMATION)
            .doctorInformation(UPDATED_DOCTOR_INFORMATION)
            .prescription(UPDATED_PRESCRIPTION)
            .date(UPDATED_DATE)
            .appoitmentStatus(UPDATED_APPOITMENT_STATUS);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        appoitment.setPatient(patient);
        // Add required entity
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            doctor = DoctorResourceIT.createUpdatedEntity(em);
            em.persist(doctor);
            em.flush();
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        appoitment.setDoctor(doctor);
        return appoitment;
    }

    @BeforeEach
    public void initTest() {
        appoitment = createEntity(em);
    }

    @Test
    @Transactional
    void createAppoitment() throws Exception {
        int databaseSizeBeforeCreate = appoitmentRepository.findAll().size();
        // Create the Appoitment
        restAppoitmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isCreated());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeCreate + 1);
        Appoitment testAppoitment = appoitmentList.get(appoitmentList.size() - 1);
        assertThat(testAppoitment.getPatientInformation()).isEqualTo(DEFAULT_PATIENT_INFORMATION);
        assertThat(testAppoitment.getDoctorInformation()).isEqualTo(DEFAULT_DOCTOR_INFORMATION);
        assertThat(testAppoitment.getPrescription()).isEqualTo(DEFAULT_PRESCRIPTION);
        assertThat(testAppoitment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAppoitment.getAppoitmentStatus()).isEqualTo(DEFAULT_APPOITMENT_STATUS);
    }

    @Test
    @Transactional
    void createAppoitmentWithExistingId() throws Exception {
        // Create the Appoitment with an existing ID
        appoitment.setId(1L);

        int databaseSizeBeforeCreate = appoitmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppoitmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppoitments() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList
        restAppoitmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appoitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientInformation").value(hasItem(DEFAULT_PATIENT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].doctorInformation").value(hasItem(DEFAULT_DOCTOR_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].prescription").value(hasItem(DEFAULT_PRESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].appoitmentStatus").value(hasItem(DEFAULT_APPOITMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getAppoitment() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get the appoitment
        restAppoitmentMockMvc
            .perform(get(ENTITY_API_URL_ID, appoitment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appoitment.getId().intValue()))
            .andExpect(jsonPath("$.patientInformation").value(DEFAULT_PATIENT_INFORMATION.toString()))
            .andExpect(jsonPath("$.doctorInformation").value(DEFAULT_DOCTOR_INFORMATION.toString()))
            .andExpect(jsonPath("$.prescription").value(DEFAULT_PRESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.appoitmentStatus").value(DEFAULT_APPOITMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getAppoitmentsByIdFiltering() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        Long id = appoitment.getId();

        defaultAppoitmentShouldBeFound("id.equals=" + id);
        defaultAppoitmentShouldNotBeFound("id.notEquals=" + id);

        defaultAppoitmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAppoitmentShouldNotBeFound("id.greaterThan=" + id);

        defaultAppoitmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAppoitmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAppoitmentsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList where date equals to DEFAULT_DATE
        defaultAppoitmentShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the appoitmentList where date equals to UPDATED_DATE
        defaultAppoitmentShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAppoitmentsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList where date in DEFAULT_DATE or UPDATED_DATE
        defaultAppoitmentShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the appoitmentList where date equals to UPDATED_DATE
        defaultAppoitmentShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAppoitmentsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList where date is not null
        defaultAppoitmentShouldBeFound("date.specified=true");

        // Get all the appoitmentList where date is null
        defaultAppoitmentShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllAppoitmentsByAppoitmentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList where appoitmentStatus equals to DEFAULT_APPOITMENT_STATUS
        defaultAppoitmentShouldBeFound("appoitmentStatus.equals=" + DEFAULT_APPOITMENT_STATUS);

        // Get all the appoitmentList where appoitmentStatus equals to UPDATED_APPOITMENT_STATUS
        defaultAppoitmentShouldNotBeFound("appoitmentStatus.equals=" + UPDATED_APPOITMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllAppoitmentsByAppoitmentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList where appoitmentStatus in DEFAULT_APPOITMENT_STATUS or UPDATED_APPOITMENT_STATUS
        defaultAppoitmentShouldBeFound("appoitmentStatus.in=" + DEFAULT_APPOITMENT_STATUS + "," + UPDATED_APPOITMENT_STATUS);

        // Get all the appoitmentList where appoitmentStatus equals to UPDATED_APPOITMENT_STATUS
        defaultAppoitmentShouldNotBeFound("appoitmentStatus.in=" + UPDATED_APPOITMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllAppoitmentsByAppoitmentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        // Get all the appoitmentList where appoitmentStatus is not null
        defaultAppoitmentShouldBeFound("appoitmentStatus.specified=true");

        // Get all the appoitmentList where appoitmentStatus is null
        defaultAppoitmentShouldNotBeFound("appoitmentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAppoitmentsByPatientIsEqualToSomething() throws Exception {
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            appoitmentRepository.saveAndFlush(appoitment);
            patient = PatientResourceIT.createEntity(em);
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        em.persist(patient);
        em.flush();
        appoitment.setPatient(patient);
        appoitmentRepository.saveAndFlush(appoitment);
        Long patientId = patient.getId();

        // Get all the appoitmentList where patient equals to patientId
        defaultAppoitmentShouldBeFound("patientId.equals=" + patientId);

        // Get all the appoitmentList where patient equals to (patientId + 1)
        defaultAppoitmentShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    @Test
    @Transactional
    void getAllAppoitmentsByDoctorIsEqualToSomething() throws Exception {
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            appoitmentRepository.saveAndFlush(appoitment);
            doctor = DoctorResourceIT.createEntity(em);
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        em.persist(doctor);
        em.flush();
        appoitment.setDoctor(doctor);
        appoitmentRepository.saveAndFlush(appoitment);
        Long doctorId = doctor.getId();

        // Get all the appoitmentList where doctor equals to doctorId
        defaultAppoitmentShouldBeFound("doctorId.equals=" + doctorId);

        // Get all the appoitmentList where doctor equals to (doctorId + 1)
        defaultAppoitmentShouldNotBeFound("doctorId.equals=" + (doctorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppoitmentShouldBeFound(String filter) throws Exception {
        restAppoitmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appoitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientInformation").value(hasItem(DEFAULT_PATIENT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].doctorInformation").value(hasItem(DEFAULT_DOCTOR_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].prescription").value(hasItem(DEFAULT_PRESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].appoitmentStatus").value(hasItem(DEFAULT_APPOITMENT_STATUS.toString())));

        // Check, that the count call also returns 1
        restAppoitmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppoitmentShouldNotBeFound(String filter) throws Exception {
        restAppoitmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppoitmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAppoitment() throws Exception {
        // Get the appoitment
        restAppoitmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppoitment() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();

        // Update the appoitment
        Appoitment updatedAppoitment = appoitmentRepository.findById(appoitment.getId()).get();
        // Disconnect from session so that the updates on updatedAppoitment are not directly saved in db
        em.detach(updatedAppoitment);
        updatedAppoitment
            .patientInformation(UPDATED_PATIENT_INFORMATION)
            .doctorInformation(UPDATED_DOCTOR_INFORMATION)
            .prescription(UPDATED_PRESCRIPTION)
            .date(UPDATED_DATE)
            .appoitmentStatus(UPDATED_APPOITMENT_STATUS);

        restAppoitmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppoitment.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAppoitment))
            )
            .andExpect(status().isOk());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
        Appoitment testAppoitment = appoitmentList.get(appoitmentList.size() - 1);
        assertThat(testAppoitment.getPatientInformation()).isEqualTo(UPDATED_PATIENT_INFORMATION);
        assertThat(testAppoitment.getDoctorInformation()).isEqualTo(UPDATED_DOCTOR_INFORMATION);
        assertThat(testAppoitment.getPrescription()).isEqualTo(UPDATED_PRESCRIPTION);
        assertThat(testAppoitment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAppoitment.getAppoitmentStatus()).isEqualTo(UPDATED_APPOITMENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAppoitment() throws Exception {
        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();
        appoitment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppoitmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appoitment.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppoitment() throws Exception {
        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();
        appoitment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppoitmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppoitment() throws Exception {
        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();
        appoitment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppoitmentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppoitmentWithPatch() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();

        // Update the appoitment using partial update
        Appoitment partialUpdatedAppoitment = new Appoitment();
        partialUpdatedAppoitment.setId(appoitment.getId());

        partialUpdatedAppoitment.patientInformation(UPDATED_PATIENT_INFORMATION).prescription(UPDATED_PRESCRIPTION).date(UPDATED_DATE);

        restAppoitmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppoitment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppoitment))
            )
            .andExpect(status().isOk());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
        Appoitment testAppoitment = appoitmentList.get(appoitmentList.size() - 1);
        assertThat(testAppoitment.getPatientInformation()).isEqualTo(UPDATED_PATIENT_INFORMATION);
        assertThat(testAppoitment.getDoctorInformation()).isEqualTo(DEFAULT_DOCTOR_INFORMATION);
        assertThat(testAppoitment.getPrescription()).isEqualTo(UPDATED_PRESCRIPTION);
        assertThat(testAppoitment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAppoitment.getAppoitmentStatus()).isEqualTo(DEFAULT_APPOITMENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAppoitmentWithPatch() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();

        // Update the appoitment using partial update
        Appoitment partialUpdatedAppoitment = new Appoitment();
        partialUpdatedAppoitment.setId(appoitment.getId());

        partialUpdatedAppoitment
            .patientInformation(UPDATED_PATIENT_INFORMATION)
            .doctorInformation(UPDATED_DOCTOR_INFORMATION)
            .prescription(UPDATED_PRESCRIPTION)
            .date(UPDATED_DATE)
            .appoitmentStatus(UPDATED_APPOITMENT_STATUS);

        restAppoitmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppoitment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppoitment))
            )
            .andExpect(status().isOk());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
        Appoitment testAppoitment = appoitmentList.get(appoitmentList.size() - 1);
        assertThat(testAppoitment.getPatientInformation()).isEqualTo(UPDATED_PATIENT_INFORMATION);
        assertThat(testAppoitment.getDoctorInformation()).isEqualTo(UPDATED_DOCTOR_INFORMATION);
        assertThat(testAppoitment.getPrescription()).isEqualTo(UPDATED_PRESCRIPTION);
        assertThat(testAppoitment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAppoitment.getAppoitmentStatus()).isEqualTo(UPDATED_APPOITMENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAppoitment() throws Exception {
        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();
        appoitment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppoitmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appoitment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppoitment() throws Exception {
        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();
        appoitment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppoitmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppoitment() throws Exception {
        int databaseSizeBeforeUpdate = appoitmentRepository.findAll().size();
        appoitment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppoitmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appoitment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appoitment in the database
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppoitment() throws Exception {
        // Initialize the database
        appoitmentRepository.saveAndFlush(appoitment);

        int databaseSizeBeforeDelete = appoitmentRepository.findAll().size();

        // Delete the appoitment
        restAppoitmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, appoitment.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appoitment> appoitmentList = appoitmentRepository.findAll();
        assertThat(appoitmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

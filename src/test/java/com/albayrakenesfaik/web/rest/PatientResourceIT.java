package com.albayrakenesfaik.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.albayrakenesfaik.IntegrationTest;
import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.domain.Patient;
import com.albayrakenesfaik.domain.enumeration.BloodType;
import com.albayrakenesfaik.domain.enumeration.Gender;
import com.albayrakenesfaik.repository.PatientRepository;
import com.albayrakenesfaik.service.criteria.PatientCriteria;
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

/**
 * Integration tests for the {@link PatientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_NUMBER = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final BloodType DEFAULT_BLOOD_TYPE = BloodType.ORH;
    private static final BloodType UPDATED_BLOOD_TYPE = BloodType.Orh;

    private static final String ENTITY_API_URL = "/api/patients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientMockMvc;

    private Patient patient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .fullName(DEFAULT_FULL_NAME)
            .email(DEFAULT_EMAIL)
            .identityNumber(DEFAULT_IDENTITY_NUMBER)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER)
            .gender(DEFAULT_GENDER)
            .password(DEFAULT_PASSWORD)
            .bloodType(DEFAULT_BLOOD_TYPE);
        return patient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .gender(UPDATED_GENDER)
            .password(UPDATED_PASSWORD)
            .bloodType(UPDATED_BLOOD_TYPE);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();
        // Create the Patient
        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getIdentityNumber()).isEqualTo(DEFAULT_IDENTITY_NUMBER);
        assertThat(testPatient.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPatient.getBloodType()).isEqualTo(DEFAULT_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void createPatientWithExistingId() throws Exception {
        // Create the Patient with an existing ID
        patient.setId(1L);

        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setFullName(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setEmail(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentityNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setIdentityNumber(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setTelephoneNumber(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setGender(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setPassword(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBloodTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setBloodType(null);

        // Create the Patient, which fails.

        restPatientMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].identityNumber").value(hasItem(DEFAULT_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE.toString())));
    }

    @Test
    @Transactional
    void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc
            .perform(get(ENTITY_API_URL_ID, patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.identityNumber").value(DEFAULT_IDENTITY_NUMBER))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.bloodType").value(DEFAULT_BLOOD_TYPE.toString()));
    }

    @Test
    @Transactional
    void getPatientsByIdFiltering() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Long id = patient.getId();

        defaultPatientShouldBeFound("id.equals=" + id);
        defaultPatientShouldNotBeFound("id.notEquals=" + id);

        defaultPatientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPatientsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where fullName equals to DEFAULT_FULL_NAME
        defaultPatientShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the patientList where fullName equals to UPDATED_FULL_NAME
        defaultPatientShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultPatientShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the patientList where fullName equals to UPDATED_FULL_NAME
        defaultPatientShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where fullName is not null
        defaultPatientShouldBeFound("fullName.specified=true");

        // Get all the patientList where fullName is null
        defaultPatientShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByFullNameContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where fullName contains DEFAULT_FULL_NAME
        defaultPatientShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the patientList where fullName contains UPDATED_FULL_NAME
        defaultPatientShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientsByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where fullName does not contain DEFAULT_FULL_NAME
        defaultPatientShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the patientList where fullName does not contain UPDATED_FULL_NAME
        defaultPatientShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where email equals to DEFAULT_EMAIL
        defaultPatientShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the patientList where email equals to UPDATED_EMAIL
        defaultPatientShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPatientsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPatientShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the patientList where email equals to UPDATED_EMAIL
        defaultPatientShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPatientsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where email is not null
        defaultPatientShouldBeFound("email.specified=true");

        // Get all the patientList where email is null
        defaultPatientShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByEmailContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where email contains DEFAULT_EMAIL
        defaultPatientShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the patientList where email contains UPDATED_EMAIL
        defaultPatientShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPatientsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where email does not contain DEFAULT_EMAIL
        defaultPatientShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the patientList where email does not contain UPDATED_EMAIL
        defaultPatientShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPatientsByIdentityNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where identityNumber equals to DEFAULT_IDENTITY_NUMBER
        defaultPatientShouldBeFound("identityNumber.equals=" + DEFAULT_IDENTITY_NUMBER);

        // Get all the patientList where identityNumber equals to UPDATED_IDENTITY_NUMBER
        defaultPatientShouldNotBeFound("identityNumber.equals=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByIdentityNumberIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where identityNumber in DEFAULT_IDENTITY_NUMBER or UPDATED_IDENTITY_NUMBER
        defaultPatientShouldBeFound("identityNumber.in=" + DEFAULT_IDENTITY_NUMBER + "," + UPDATED_IDENTITY_NUMBER);

        // Get all the patientList where identityNumber equals to UPDATED_IDENTITY_NUMBER
        defaultPatientShouldNotBeFound("identityNumber.in=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByIdentityNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where identityNumber is not null
        defaultPatientShouldBeFound("identityNumber.specified=true");

        // Get all the patientList where identityNumber is null
        defaultPatientShouldNotBeFound("identityNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByIdentityNumberContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where identityNumber contains DEFAULT_IDENTITY_NUMBER
        defaultPatientShouldBeFound("identityNumber.contains=" + DEFAULT_IDENTITY_NUMBER);

        // Get all the patientList where identityNumber contains UPDATED_IDENTITY_NUMBER
        defaultPatientShouldNotBeFound("identityNumber.contains=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByIdentityNumberNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where identityNumber does not contain DEFAULT_IDENTITY_NUMBER
        defaultPatientShouldNotBeFound("identityNumber.doesNotContain=" + DEFAULT_IDENTITY_NUMBER);

        // Get all the patientList where identityNumber does not contain UPDATED_IDENTITY_NUMBER
        defaultPatientShouldBeFound("identityNumber.doesNotContain=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByTelephoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where telephoneNumber equals to DEFAULT_TELEPHONE_NUMBER
        defaultPatientShouldBeFound("telephoneNumber.equals=" + DEFAULT_TELEPHONE_NUMBER);

        // Get all the patientList where telephoneNumber equals to UPDATED_TELEPHONE_NUMBER
        defaultPatientShouldNotBeFound("telephoneNumber.equals=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByTelephoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where telephoneNumber in DEFAULT_TELEPHONE_NUMBER or UPDATED_TELEPHONE_NUMBER
        defaultPatientShouldBeFound("telephoneNumber.in=" + DEFAULT_TELEPHONE_NUMBER + "," + UPDATED_TELEPHONE_NUMBER);

        // Get all the patientList where telephoneNumber equals to UPDATED_TELEPHONE_NUMBER
        defaultPatientShouldNotBeFound("telephoneNumber.in=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByTelephoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where telephoneNumber is not null
        defaultPatientShouldBeFound("telephoneNumber.specified=true");

        // Get all the patientList where telephoneNumber is null
        defaultPatientShouldNotBeFound("telephoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByTelephoneNumberContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where telephoneNumber contains DEFAULT_TELEPHONE_NUMBER
        defaultPatientShouldBeFound("telephoneNumber.contains=" + DEFAULT_TELEPHONE_NUMBER);

        // Get all the patientList where telephoneNumber contains UPDATED_TELEPHONE_NUMBER
        defaultPatientShouldNotBeFound("telephoneNumber.contains=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByTelephoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where telephoneNumber does not contain DEFAULT_TELEPHONE_NUMBER
        defaultPatientShouldNotBeFound("telephoneNumber.doesNotContain=" + DEFAULT_TELEPHONE_NUMBER);

        // Get all the patientList where telephoneNumber does not contain UPDATED_TELEPHONE_NUMBER
        defaultPatientShouldBeFound("telephoneNumber.doesNotContain=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender equals to DEFAULT_GENDER
        defaultPatientShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the patientList where gender equals to UPDATED_GENDER
        defaultPatientShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPatientShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the patientList where gender equals to UPDATED_GENDER
        defaultPatientShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender is not null
        defaultPatientShouldBeFound("gender.specified=true");

        // Get all the patientList where gender is null
        defaultPatientShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where password equals to DEFAULT_PASSWORD
        defaultPatientShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the patientList where password equals to UPDATED_PASSWORD
        defaultPatientShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPatientsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultPatientShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the patientList where password equals to UPDATED_PASSWORD
        defaultPatientShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPatientsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where password is not null
        defaultPatientShouldBeFound("password.specified=true");

        // Get all the patientList where password is null
        defaultPatientShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where password contains DEFAULT_PASSWORD
        defaultPatientShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the patientList where password contains UPDATED_PASSWORD
        defaultPatientShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPatientsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where password does not contain DEFAULT_PASSWORD
        defaultPatientShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the patientList where password does not contain UPDATED_PASSWORD
        defaultPatientShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPatientsByBloodTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where bloodType equals to DEFAULT_BLOOD_TYPE
        defaultPatientShouldBeFound("bloodType.equals=" + DEFAULT_BLOOD_TYPE);

        // Get all the patientList where bloodType equals to UPDATED_BLOOD_TYPE
        defaultPatientShouldNotBeFound("bloodType.equals=" + UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientsByBloodTypeIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where bloodType in DEFAULT_BLOOD_TYPE or UPDATED_BLOOD_TYPE
        defaultPatientShouldBeFound("bloodType.in=" + DEFAULT_BLOOD_TYPE + "," + UPDATED_BLOOD_TYPE);

        // Get all the patientList where bloodType equals to UPDATED_BLOOD_TYPE
        defaultPatientShouldNotBeFound("bloodType.in=" + UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientsByBloodTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where bloodType is not null
        defaultPatientShouldBeFound("bloodType.specified=true");

        // Get all the patientList where bloodType is null
        defaultPatientShouldNotBeFound("bloodType.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByAppoitmentIsEqualToSomething() throws Exception {
        Appoitment appoitment;
        if (TestUtil.findAll(em, Appoitment.class).isEmpty()) {
            patientRepository.saveAndFlush(patient);
            appoitment = AppoitmentResourceIT.createEntity(em);
        } else {
            appoitment = TestUtil.findAll(em, Appoitment.class).get(0);
        }
        em.persist(appoitment);
        em.flush();
        patient.addAppoitment(appoitment);
        patientRepository.saveAndFlush(patient);
        Long appoitmentId = appoitment.getId();

        // Get all the patientList where appoitment equals to appoitmentId
        defaultPatientShouldBeFound("appoitmentId.equals=" + appoitmentId);

        // Get all the patientList where appoitment equals to (appoitmentId + 1)
        defaultPatientShouldNotBeFound("appoitmentId.equals=" + (appoitmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientShouldBeFound(String filter) throws Exception {
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].identityNumber").value(hasItem(DEFAULT_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE.toString())));

        // Check, that the count call also returns 1
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientShouldNotBeFound(String filter) throws Exception {
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .gender(UPDATED_GENDER)
            .password(UPDATED_PASSWORD)
            .bloodType(UPDATED_BLOOD_TYPE);

        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPatient.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testPatient.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPatient.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patient.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .password(UPDATED_PASSWORD);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testPatient.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPatient.getBloodType()).isEqualTo(DEFAULT_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .gender(UPDATED_GENDER)
            .password(UPDATED_PASSWORD)
            .bloodType(UPDATED_BLOOD_TYPE);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testPatient.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPatient.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patient.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc
            .perform(delete(ENTITY_API_URL_ID, patient.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

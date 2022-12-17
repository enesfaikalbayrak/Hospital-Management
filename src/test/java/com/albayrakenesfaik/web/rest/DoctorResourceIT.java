package com.albayrakenesfaik.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.albayrakenesfaik.IntegrationTest;
import com.albayrakenesfaik.domain.Appoitment;
import com.albayrakenesfaik.domain.Department;
import com.albayrakenesfaik.domain.Doctor;
import com.albayrakenesfaik.domain.enumeration.BloodType;
import com.albayrakenesfaik.domain.enumeration.Gender;
import com.albayrakenesfaik.repository.DoctorRepository;
import com.albayrakenesfaik.service.criteria.DoctorCriteria;
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
 * Integration tests for the {@link DoctorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorResourceIT {

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

    private static final String DEFAULT_SPECIALIST = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALIST = "BBBBBBBBBB";

    private static final String DEFAULT_AVALIABLE_TIMES = "AAAAAAAAAA";
    private static final String UPDATED_AVALIABLE_TIMES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doctors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity(EntityManager em) {
        Doctor doctor = new Doctor()
            .fullName(DEFAULT_FULL_NAME)
            .email(DEFAULT_EMAIL)
            .identityNumber(DEFAULT_IDENTITY_NUMBER)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER)
            .gender(DEFAULT_GENDER)
            .password(DEFAULT_PASSWORD)
            .bloodType(DEFAULT_BLOOD_TYPE)
            .specialist(DEFAULT_SPECIALIST)
            .avaliableTimes(DEFAULT_AVALIABLE_TIMES);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        doctor.setDepartment(department);
        return doctor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createUpdatedEntity(EntityManager em) {
        Doctor doctor = new Doctor()
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .gender(UPDATED_GENDER)
            .password(UPDATED_PASSWORD)
            .bloodType(UPDATED_BLOOD_TYPE)
            .specialist(UPDATED_SPECIALIST)
            .avaliableTimes(UPDATED_AVALIABLE_TIMES);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        doctor.setDepartment(department);
        return doctor;
    }

    @BeforeEach
    public void initTest() {
        doctor = createEntity(em);
    }

    @Test
    @Transactional
    void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();
        // Create the Doctor
        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testDoctor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDoctor.getIdentityNumber()).isEqualTo(DEFAULT_IDENTITY_NUMBER);
        assertThat(testDoctor.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testDoctor.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testDoctor.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDoctor.getBloodType()).isEqualTo(DEFAULT_BLOOD_TYPE);
        assertThat(testDoctor.getSpecialist()).isEqualTo(DEFAULT_SPECIALIST);
        assertThat(testDoctor.getAvaliableTimes()).isEqualTo(DEFAULT_AVALIABLE_TIMES);
    }

    @Test
    @Transactional
    void createDoctorWithExistingId() throws Exception {
        // Create the Doctor with an existing ID
        doctor.setId(1L);

        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setFullName(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setEmail(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentityNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setIdentityNumber(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setTelephoneNumber(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setGender(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setPassword(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBloodTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setBloodType(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpecialistIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setSpecialist(null);

        // Create the Doctor, which fails.

        restDoctorMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].identityNumber").value(hasItem(DEFAULT_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].specialist").value(hasItem(DEFAULT_SPECIALIST)))
            .andExpect(jsonPath("$.[*].avaliableTimes").value(hasItem(DEFAULT_AVALIABLE_TIMES.toString())));
    }

    @Test
    @Transactional
    void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL_ID, doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.identityNumber").value(DEFAULT_IDENTITY_NUMBER))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.bloodType").value(DEFAULT_BLOOD_TYPE.toString()))
            .andExpect(jsonPath("$.specialist").value(DEFAULT_SPECIALIST))
            .andExpect(jsonPath("$.avaliableTimes").value(DEFAULT_AVALIABLE_TIMES.toString()));
    }

    @Test
    @Transactional
    void getDoctorsByIdFiltering() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        Long id = doctor.getId();

        defaultDoctorShouldBeFound("id.equals=" + id);
        defaultDoctorShouldNotBeFound("id.notEquals=" + id);

        defaultDoctorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDoctorShouldNotBeFound("id.greaterThan=" + id);

        defaultDoctorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDoctorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDoctorsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where fullName equals to DEFAULT_FULL_NAME
        defaultDoctorShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the doctorList where fullName equals to UPDATED_FULL_NAME
        defaultDoctorShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDoctorsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultDoctorShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the doctorList where fullName equals to UPDATED_FULL_NAME
        defaultDoctorShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDoctorsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where fullName is not null
        defaultDoctorShouldBeFound("fullName.specified=true");

        // Get all the doctorList where fullName is null
        defaultDoctorShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsByFullNameContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where fullName contains DEFAULT_FULL_NAME
        defaultDoctorShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the doctorList where fullName contains UPDATED_FULL_NAME
        defaultDoctorShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDoctorsByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where fullName does not contain DEFAULT_FULL_NAME
        defaultDoctorShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the doctorList where fullName does not contain UPDATED_FULL_NAME
        defaultDoctorShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDoctorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email equals to DEFAULT_EMAIL
        defaultDoctorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the doctorList where email equals to UPDATED_EMAIL
        defaultDoctorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDoctorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDoctorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the doctorList where email equals to UPDATED_EMAIL
        defaultDoctorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDoctorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email is not null
        defaultDoctorShouldBeFound("email.specified=true");

        // Get all the doctorList where email is null
        defaultDoctorShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email contains DEFAULT_EMAIL
        defaultDoctorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the doctorList where email contains UPDATED_EMAIL
        defaultDoctorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDoctorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email does not contain DEFAULT_EMAIL
        defaultDoctorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the doctorList where email does not contain UPDATED_EMAIL
        defaultDoctorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDoctorsByIdentityNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where identityNumber equals to DEFAULT_IDENTITY_NUMBER
        defaultDoctorShouldBeFound("identityNumber.equals=" + DEFAULT_IDENTITY_NUMBER);

        // Get all the doctorList where identityNumber equals to UPDATED_IDENTITY_NUMBER
        defaultDoctorShouldNotBeFound("identityNumber.equals=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByIdentityNumberIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where identityNumber in DEFAULT_IDENTITY_NUMBER or UPDATED_IDENTITY_NUMBER
        defaultDoctorShouldBeFound("identityNumber.in=" + DEFAULT_IDENTITY_NUMBER + "," + UPDATED_IDENTITY_NUMBER);

        // Get all the doctorList where identityNumber equals to UPDATED_IDENTITY_NUMBER
        defaultDoctorShouldNotBeFound("identityNumber.in=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByIdentityNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where identityNumber is not null
        defaultDoctorShouldBeFound("identityNumber.specified=true");

        // Get all the doctorList where identityNumber is null
        defaultDoctorShouldNotBeFound("identityNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsByIdentityNumberContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where identityNumber contains DEFAULT_IDENTITY_NUMBER
        defaultDoctorShouldBeFound("identityNumber.contains=" + DEFAULT_IDENTITY_NUMBER);

        // Get all the doctorList where identityNumber contains UPDATED_IDENTITY_NUMBER
        defaultDoctorShouldNotBeFound("identityNumber.contains=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByIdentityNumberNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where identityNumber does not contain DEFAULT_IDENTITY_NUMBER
        defaultDoctorShouldNotBeFound("identityNumber.doesNotContain=" + DEFAULT_IDENTITY_NUMBER);

        // Get all the doctorList where identityNumber does not contain UPDATED_IDENTITY_NUMBER
        defaultDoctorShouldBeFound("identityNumber.doesNotContain=" + UPDATED_IDENTITY_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByTelephoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telephoneNumber equals to DEFAULT_TELEPHONE_NUMBER
        defaultDoctorShouldBeFound("telephoneNumber.equals=" + DEFAULT_TELEPHONE_NUMBER);

        // Get all the doctorList where telephoneNumber equals to UPDATED_TELEPHONE_NUMBER
        defaultDoctorShouldNotBeFound("telephoneNumber.equals=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByTelephoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telephoneNumber in DEFAULT_TELEPHONE_NUMBER or UPDATED_TELEPHONE_NUMBER
        defaultDoctorShouldBeFound("telephoneNumber.in=" + DEFAULT_TELEPHONE_NUMBER + "," + UPDATED_TELEPHONE_NUMBER);

        // Get all the doctorList where telephoneNumber equals to UPDATED_TELEPHONE_NUMBER
        defaultDoctorShouldNotBeFound("telephoneNumber.in=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByTelephoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telephoneNumber is not null
        defaultDoctorShouldBeFound("telephoneNumber.specified=true");

        // Get all the doctorList where telephoneNumber is null
        defaultDoctorShouldNotBeFound("telephoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsByTelephoneNumberContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telephoneNumber contains DEFAULT_TELEPHONE_NUMBER
        defaultDoctorShouldBeFound("telephoneNumber.contains=" + DEFAULT_TELEPHONE_NUMBER);

        // Get all the doctorList where telephoneNumber contains UPDATED_TELEPHONE_NUMBER
        defaultDoctorShouldNotBeFound("telephoneNumber.contains=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByTelephoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telephoneNumber does not contain DEFAULT_TELEPHONE_NUMBER
        defaultDoctorShouldNotBeFound("telephoneNumber.doesNotContain=" + DEFAULT_TELEPHONE_NUMBER);

        // Get all the doctorList where telephoneNumber does not contain UPDATED_TELEPHONE_NUMBER
        defaultDoctorShouldBeFound("telephoneNumber.doesNotContain=" + UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDoctorsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where gender equals to DEFAULT_GENDER
        defaultDoctorShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the doctorList where gender equals to UPDATED_GENDER
        defaultDoctorShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllDoctorsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultDoctorShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the doctorList where gender equals to UPDATED_GENDER
        defaultDoctorShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllDoctorsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where gender is not null
        defaultDoctorShouldBeFound("gender.specified=true");

        // Get all the doctorList where gender is null
        defaultDoctorShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where password equals to DEFAULT_PASSWORD
        defaultDoctorShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the doctorList where password equals to UPDATED_PASSWORD
        defaultDoctorShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllDoctorsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultDoctorShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the doctorList where password equals to UPDATED_PASSWORD
        defaultDoctorShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllDoctorsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where password is not null
        defaultDoctorShouldBeFound("password.specified=true");

        // Get all the doctorList where password is null
        defaultDoctorShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where password contains DEFAULT_PASSWORD
        defaultDoctorShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the doctorList where password contains UPDATED_PASSWORD
        defaultDoctorShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllDoctorsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where password does not contain DEFAULT_PASSWORD
        defaultDoctorShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the doctorList where password does not contain UPDATED_PASSWORD
        defaultDoctorShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllDoctorsByBloodTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where bloodType equals to DEFAULT_BLOOD_TYPE
        defaultDoctorShouldBeFound("bloodType.equals=" + DEFAULT_BLOOD_TYPE);

        // Get all the doctorList where bloodType equals to UPDATED_BLOOD_TYPE
        defaultDoctorShouldNotBeFound("bloodType.equals=" + UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void getAllDoctorsByBloodTypeIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where bloodType in DEFAULT_BLOOD_TYPE or UPDATED_BLOOD_TYPE
        defaultDoctorShouldBeFound("bloodType.in=" + DEFAULT_BLOOD_TYPE + "," + UPDATED_BLOOD_TYPE);

        // Get all the doctorList where bloodType equals to UPDATED_BLOOD_TYPE
        defaultDoctorShouldNotBeFound("bloodType.in=" + UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    void getAllDoctorsByBloodTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where bloodType is not null
        defaultDoctorShouldBeFound("bloodType.specified=true");

        // Get all the doctorList where bloodType is null
        defaultDoctorShouldNotBeFound("bloodType.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsBySpecialistIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where specialist equals to DEFAULT_SPECIALIST
        defaultDoctorShouldBeFound("specialist.equals=" + DEFAULT_SPECIALIST);

        // Get all the doctorList where specialist equals to UPDATED_SPECIALIST
        defaultDoctorShouldNotBeFound("specialist.equals=" + UPDATED_SPECIALIST);
    }

    @Test
    @Transactional
    void getAllDoctorsBySpecialistIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where specialist in DEFAULT_SPECIALIST or UPDATED_SPECIALIST
        defaultDoctorShouldBeFound("specialist.in=" + DEFAULT_SPECIALIST + "," + UPDATED_SPECIALIST);

        // Get all the doctorList where specialist equals to UPDATED_SPECIALIST
        defaultDoctorShouldNotBeFound("specialist.in=" + UPDATED_SPECIALIST);
    }

    @Test
    @Transactional
    void getAllDoctorsBySpecialistIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where specialist is not null
        defaultDoctorShouldBeFound("specialist.specified=true");

        // Get all the doctorList where specialist is null
        defaultDoctorShouldNotBeFound("specialist.specified=false");
    }

    @Test
    @Transactional
    void getAllDoctorsBySpecialistContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where specialist contains DEFAULT_SPECIALIST
        defaultDoctorShouldBeFound("specialist.contains=" + DEFAULT_SPECIALIST);

        // Get all the doctorList where specialist contains UPDATED_SPECIALIST
        defaultDoctorShouldNotBeFound("specialist.contains=" + UPDATED_SPECIALIST);
    }

    @Test
    @Transactional
    void getAllDoctorsBySpecialistNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where specialist does not contain DEFAULT_SPECIALIST
        defaultDoctorShouldNotBeFound("specialist.doesNotContain=" + DEFAULT_SPECIALIST);

        // Get all the doctorList where specialist does not contain UPDATED_SPECIALIST
        defaultDoctorShouldBeFound("specialist.doesNotContain=" + UPDATED_SPECIALIST);
    }

    @Test
    @Transactional
    void getAllDoctorsByAppoitmentIsEqualToSomething() throws Exception {
        Appoitment appoitment;
        if (TestUtil.findAll(em, Appoitment.class).isEmpty()) {
            doctorRepository.saveAndFlush(doctor);
            appoitment = AppoitmentResourceIT.createEntity(em);
        } else {
            appoitment = TestUtil.findAll(em, Appoitment.class).get(0);
        }
        em.persist(appoitment);
        em.flush();
        doctor.addAppoitment(appoitment);
        doctorRepository.saveAndFlush(doctor);
        Long appoitmentId = appoitment.getId();

        // Get all the doctorList where appoitment equals to appoitmentId
        defaultDoctorShouldBeFound("appoitmentId.equals=" + appoitmentId);

        // Get all the doctorList where appoitment equals to (appoitmentId + 1)
        defaultDoctorShouldNotBeFound("appoitmentId.equals=" + (appoitmentId + 1));
    }

    @Test
    @Transactional
    void getAllDoctorsByDepartmentIsEqualToSomething() throws Exception {
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            doctorRepository.saveAndFlush(doctor);
            department = DepartmentResourceIT.createEntity(em);
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        em.persist(department);
        em.flush();
        doctor.setDepartment(department);
        doctorRepository.saveAndFlush(doctor);
        Long departmentId = department.getId();

        // Get all the doctorList where department equals to departmentId
        defaultDoctorShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the doctorList where department equals to (departmentId + 1)
        defaultDoctorShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDoctorShouldBeFound(String filter) throws Exception {
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].identityNumber").value(hasItem(DEFAULT_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].specialist").value(hasItem(DEFAULT_SPECIALIST)))
            .andExpect(jsonPath("$.[*].avaliableTimes").value(hasItem(DEFAULT_AVALIABLE_TIMES.toString())));

        // Check, that the count call also returns 1
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDoctorShouldNotBeFound(String filter) throws Exception {
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findById(doctor.getId()).get();
        // Disconnect from session so that the updates on updatedDoctor are not directly saved in db
        em.detach(updatedDoctor);
        updatedDoctor
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .gender(UPDATED_GENDER)
            .password(UPDATED_PASSWORD)
            .bloodType(UPDATED_BLOOD_TYPE)
            .specialist(UPDATED_SPECIALIST)
            .avaliableTimes(UPDATED_AVALIABLE_TIMES);

        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoctor.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDoctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctor.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testDoctor.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testDoctor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testDoctor.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDoctor.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
        assertThat(testDoctor.getSpecialist()).isEqualTo(UPDATED_SPECIALIST);
        assertThat(testDoctor.getAvaliableTimes()).isEqualTo(UPDATED_AVALIABLE_TIMES);
    }

    @Test
    @Transactional
    void putNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctor.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorWithPatch() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor using partial update
        Doctor partialUpdatedDoctor = new Doctor();
        partialUpdatedDoctor.setId(doctor.getId());

        partialUpdatedDoctor
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .bloodType(UPDATED_BLOOD_TYPE)
            .specialist(UPDATED_SPECIALIST)
            .avaliableTimes(UPDATED_AVALIABLE_TIMES);

        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDoctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctor.getIdentityNumber()).isEqualTo(DEFAULT_IDENTITY_NUMBER);
        assertThat(testDoctor.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testDoctor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testDoctor.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDoctor.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
        assertThat(testDoctor.getSpecialist()).isEqualTo(UPDATED_SPECIALIST);
        assertThat(testDoctor.getAvaliableTimes()).isEqualTo(UPDATED_AVALIABLE_TIMES);
    }

    @Test
    @Transactional
    void fullUpdateDoctorWithPatch() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor using partial update
        Doctor partialUpdatedDoctor = new Doctor();
        partialUpdatedDoctor.setId(doctor.getId());

        partialUpdatedDoctor
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .gender(UPDATED_GENDER)
            .password(UPDATED_PASSWORD)
            .bloodType(UPDATED_BLOOD_TYPE)
            .specialist(UPDATED_SPECIALIST)
            .avaliableTimes(UPDATED_AVALIABLE_TIMES);

        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDoctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctor.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testDoctor.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testDoctor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testDoctor.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDoctor.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
        assertThat(testDoctor.getSpecialist()).isEqualTo(UPDATED_SPECIALIST);
        assertThat(testDoctor.getAvaliableTimes()).isEqualTo(UPDATED_AVALIABLE_TIMES);
    }

    @Test
    @Transactional
    void patchNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Delete the doctor
        restDoctorMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

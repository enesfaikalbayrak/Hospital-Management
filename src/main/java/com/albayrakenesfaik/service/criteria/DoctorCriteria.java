package com.albayrakenesfaik.service.criteria;

import com.albayrakenesfaik.domain.enumeration.BloodType;
import com.albayrakenesfaik.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.albayrakenesfaik.domain.Doctor} entity. This class is used
 * in {@link com.albayrakenesfaik.web.rest.DoctorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /doctors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    /**
     * Class for filtering BloodType
     */
    public static class BloodTypeFilter extends Filter<BloodType> {

        public BloodTypeFilter() {}

        public BloodTypeFilter(BloodTypeFilter filter) {
            super(filter);
        }

        @Override
        public BloodTypeFilter copy() {
            return new BloodTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter email;

    private StringFilter identityNumber;

    private StringFilter telephoneNumber;

    private GenderFilter gender;

    private StringFilter password;

    private BloodTypeFilter bloodType;

    private StringFilter specialist;

    private LongFilter appoitmentId;

    private LongFilter departmentId;

    private Boolean distinct;

    public DoctorCriteria() {}

    public DoctorCriteria(DoctorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.identityNumber = other.identityNumber == null ? null : other.identityNumber.copy();
        this.telephoneNumber = other.telephoneNumber == null ? null : other.telephoneNumber.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.bloodType = other.bloodType == null ? null : other.bloodType.copy();
        this.specialist = other.specialist == null ? null : other.specialist.copy();
        this.appoitmentId = other.appoitmentId == null ? null : other.appoitmentId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DoctorCriteria copy() {
        return new DoctorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getIdentityNumber() {
        return identityNumber;
    }

    public StringFilter identityNumber() {
        if (identityNumber == null) {
            identityNumber = new StringFilter();
        }
        return identityNumber;
    }

    public void setIdentityNumber(StringFilter identityNumber) {
        this.identityNumber = identityNumber;
    }

    public StringFilter getTelephoneNumber() {
        return telephoneNumber;
    }

    public StringFilter telephoneNumber() {
        if (telephoneNumber == null) {
            telephoneNumber = new StringFilter();
        }
        return telephoneNumber;
    }

    public void setTelephoneNumber(StringFilter telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public GenderFilter gender() {
        if (gender == null) {
            gender = new GenderFilter();
        }
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public BloodTypeFilter getBloodType() {
        return bloodType;
    }

    public BloodTypeFilter bloodType() {
        if (bloodType == null) {
            bloodType = new BloodTypeFilter();
        }
        return bloodType;
    }

    public void setBloodType(BloodTypeFilter bloodType) {
        this.bloodType = bloodType;
    }

    public StringFilter getSpecialist() {
        return specialist;
    }

    public StringFilter specialist() {
        if (specialist == null) {
            specialist = new StringFilter();
        }
        return specialist;
    }

    public void setSpecialist(StringFilter specialist) {
        this.specialist = specialist;
    }

    public LongFilter getAppoitmentId() {
        return appoitmentId;
    }

    public LongFilter appoitmentId() {
        if (appoitmentId == null) {
            appoitmentId = new LongFilter();
        }
        return appoitmentId;
    }

    public void setAppoitmentId(LongFilter appoitmentId) {
        this.appoitmentId = appoitmentId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DoctorCriteria that = (DoctorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(identityNumber, that.identityNumber) &&
            Objects.equals(telephoneNumber, that.telephoneNumber) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(password, that.password) &&
            Objects.equals(bloodType, that.bloodType) &&
            Objects.equals(specialist, that.specialist) &&
            Objects.equals(appoitmentId, that.appoitmentId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fullName,
            email,
            identityNumber,
            telephoneNumber,
            gender,
            password,
            bloodType,
            specialist,
            appoitmentId,
            departmentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (identityNumber != null ? "identityNumber=" + identityNumber + ", " : "") +
            (telephoneNumber != null ? "telephoneNumber=" + telephoneNumber + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (bloodType != null ? "bloodType=" + bloodType + ", " : "") +
            (specialist != null ? "specialist=" + specialist + ", " : "") +
            (appoitmentId != null ? "appoitmentId=" + appoitmentId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

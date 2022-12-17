package com.albayrakenesfaik.service.criteria;

import com.albayrakenesfaik.domain.enumeration.AppoitmentStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.albayrakenesfaik.domain.Appoitment} entity. This class is used
 * in {@link com.albayrakenesfaik.web.rest.AppoitmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appoitments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppoitmentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AppoitmentStatus
     */
    public static class AppoitmentStatusFilter extends Filter<AppoitmentStatus> {

        public AppoitmentStatusFilter() {}

        public AppoitmentStatusFilter(AppoitmentStatusFilter filter) {
            super(filter);
        }

        @Override
        public AppoitmentStatusFilter copy() {
            return new AppoitmentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private AppoitmentStatusFilter appoitmentStatus;

    private LongFilter patientId;

    private LongFilter doctorId;

    private Boolean distinct;

    public AppoitmentCriteria() {}

    public AppoitmentCriteria(AppoitmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.appoitmentStatus = other.appoitmentStatus == null ? null : other.appoitmentStatus.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
        this.doctorId = other.doctorId == null ? null : other.doctorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AppoitmentCriteria copy() {
        return new AppoitmentCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public AppoitmentStatusFilter getAppoitmentStatus() {
        return appoitmentStatus;
    }

    public AppoitmentStatusFilter appoitmentStatus() {
        if (appoitmentStatus == null) {
            appoitmentStatus = new AppoitmentStatusFilter();
        }
        return appoitmentStatus;
    }

    public void setAppoitmentStatus(AppoitmentStatusFilter appoitmentStatus) {
        this.appoitmentStatus = appoitmentStatus;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public LongFilter patientId() {
        if (patientId == null) {
            patientId = new LongFilter();
        }
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public LongFilter getDoctorId() {
        return doctorId;
    }

    public LongFilter doctorId() {
        if (doctorId == null) {
            doctorId = new LongFilter();
        }
        return doctorId;
    }

    public void setDoctorId(LongFilter doctorId) {
        this.doctorId = doctorId;
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
        final AppoitmentCriteria that = (AppoitmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(appoitmentStatus, that.appoitmentStatus) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(doctorId, that.doctorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, appoitmentStatus, patientId, doctorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppoitmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (appoitmentStatus != null ? "appoitmentStatus=" + appoitmentStatus + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            (doctorId != null ? "doctorId=" + doctorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

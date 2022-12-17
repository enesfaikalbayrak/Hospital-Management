package com.albayrakenesfaik.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.albayrakenesfaik.domain.Prescription} entity. This class is used
 * in {@link com.albayrakenesfaik.web.rest.PrescriptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prescriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrescriptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter note;

    private Boolean distinct;

    public PrescriptionCriteria() {}

    public PrescriptionCriteria(PrescriptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PrescriptionCriteria copy() {
        return new PrescriptionCriteria(this);
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

    public StringFilter getNote() {
        return note;
    }

    public StringFilter note() {
        if (note == null) {
            note = new StringFilter();
        }
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
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
        final PrescriptionCriteria that = (PrescriptionCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(note, that.note) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrescriptionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (note != null ? "note=" + note + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

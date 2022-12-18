package com.albayrakenesfaik.domain.dto.entity;

public class PrescriptionDTO {
    private String note;
    private Medicines medicines;

    public String getNote() {
        return note;
    }

    public PrescriptionDTO setNote(String note) {
        this.note = note;
        return this;
    }

    public Medicines getMedicines() {
        return medicines;
    }

    public PrescriptionDTO setMedicines(Medicines medicines) {
        this.medicines = medicines;
        return this;
    }

    @Override
    public String toString() {
        return "PrescriptionDTO{" +
            "note='" + note + '\'' +
            ", medicines=" + medicines +
            '}';
    }
}

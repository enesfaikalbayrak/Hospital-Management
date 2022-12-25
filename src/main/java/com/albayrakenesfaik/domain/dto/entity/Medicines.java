package com.albayrakenesfaik.domain.dto.entity;

import java.util.List;

public class Medicines {
    private List<String> medicineList;

    public List<String> getMedicineList() {
        return medicineList;
    }

    public Medicines setMedicineList(List<String> medicineList) {
        this.medicineList = medicineList;
        return this;
    }

    @Override
    public String toString() {
        return "Medicines{" +
            "medicineList=" + medicineList +
            '}';
    }
}


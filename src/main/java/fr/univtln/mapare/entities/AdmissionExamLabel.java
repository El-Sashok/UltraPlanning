package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class AdmissionExamLabel implements Entity {
    private long id;
    private String label;
    private static final List<AdmissionExamLabel> ADMISSIONEXAMLABELS = new ArrayList<>();

    //Constructor
    public AdmissionExamLabel(long id, String label) {
        this.id = id;
        this.label = label;
        if (id != -1)
            ADMISSIONEXAMLABELS.add(this);
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static List<AdmissionExamLabel> getAdmissionExamLabelList() {
        return ADMISSIONEXAMLABELS;
    }

    public void popAdmissionExamLabelInList(AdmissionExamLabel admissionExamLabel) {
        ADMISSIONEXAMLABELS.remove(admissionExamLabel);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //Methods
    @Override
    public String toString() {
        return "AdmissionExamLabel{" +
                "label=" + label +
                ", id=" + id + '}';
    }

}
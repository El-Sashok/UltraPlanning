package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class AdmissionExam {
    private String label;

    public AdmissionExam(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static List<AdmissionExam> getAdmissionExamList() {
        List<AdmissionExam> placeholder = new ArrayList<AdmissionExam>();
        placeholder.add(new AdmissionExam("TOEIC"));
        placeholder.add(new AdmissionExam("PIX"));
        return placeholder;
    }
}

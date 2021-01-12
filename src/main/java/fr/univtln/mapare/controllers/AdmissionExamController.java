package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamDAO;
import fr.univtln.mapare.entities.AdmissionExam;
import fr.univtln.mapare.entities.AdmissionExamLabel;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Student;

import java.sql.SQLException;
import java.util.List;

public abstract class AdmissionExamController {

    private AdmissionExamController() {}

    public static void createAdmissionExam(Reservation reservation, AdmissionExamLabel label, List<Student> students)
            throws SQLException {
        AdmissionExam aem = new AdmissionExam(reservation, label);
        for (Student s : students)
            aem.addStudent(s);
        try (AdmissionExamDAO aemDAO = new AdmissionExamDAO()) {
            aemDAO.persist(aem);
        }
    }

}

package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamLabelDAO;
import fr.univtln.mapare.daos.TeacherDAO;
import fr.univtln.mapare.entities.AdmissionExamLabel;

import java.sql.SQLException;

public abstract class AdmissionExamLabelController {
    public static void loadLabels() throws SQLException {
        /*AdmissionExamLabelDAO labelDAO = new AdmissionExamLabelDAO();
        labelDAO.findAll();
        labelDAO.close();*/
        new AdmissionExamLabel(1, "TOEIC");
        new AdmissionExamLabel(2, "PIX");
    }
}

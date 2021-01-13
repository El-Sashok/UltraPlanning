package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamLabelDAO;

import java.sql.SQLException;

public abstract class AdmissionExamLabelController {

    // SonarLint wants a private constructor for some reason
    private AdmissionExamLabelController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les intitulés de concours
     * @throws SQLException Exception SQL
     */
    public static void loadLabels() throws SQLException {
        try (AdmissionExamLabelDAO labelDAO = new AdmissionExamLabelDAO()) {
            labelDAO.findAll();
        }
    }
}

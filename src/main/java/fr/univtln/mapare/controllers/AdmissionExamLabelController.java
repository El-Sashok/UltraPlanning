package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamLabelDAO;
import fr.univtln.mapare.entities.AdmissionExamLabel;

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

    /**
     * Permet de créer un label pour un concours
     * @param label Le nom du concours
     * @throws SQLException Exception SQL
     */
    public static void createLabel(String label) throws SQLException {
        AdmissionExamLabel admissionExamLabel = new AdmissionExamLabel(-1, label);
        try (AdmissionExamLabelDAO labelDAO = new AdmissionExamLabelDAO()) {
            labelDAO.persist(admissionExamLabel);
        }
    }
}

package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamLabelDAO;
import fr.univtln.mapare.entities.AdmissionExamLabel;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

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
     * Permet de supprimer un concours
     * @param admissionExamLabel Le concours à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(AdmissionExamLabel admissionExamLabel) throws SQLException {
        try (AdmissionExamLabelDAO admissionExamLabelDAO = new AdmissionExamLabelDAO()) {
            admissionExamLabelDAO.remove(admissionExamLabel.getId());
        }
        AdmissionExamLabel.popAdmissionExamLabelInList(admissionExamLabel);
    }

    /**
     * Permet de créer un intitulé pour un concours
     * @param label Le nom du concours
     * @throws SQLException Exception SQL
     */
    public static void createLabel(String label) throws SQLException {
        AdmissionExamLabel admissionExamLabel = new AdmissionExamLabel(-1, label);
        try (AdmissionExamLabelDAO labelDAO = new AdmissionExamLabelDAO()) {
            labelDAO.persist(admissionExamLabel);
        }
    }

    /**
     * Permet de changer le nom donné à l'intitulé d'un concours
     * @param admissionExamLabel L'intitulé d'un concours
     * @param label Le nom du concours
     * @throws SQLException Exception SQL
     */
    public static void changeLabel(AdmissionExamLabel admissionExamLabel, String label) throws SQLException, EmptyAttributeException, NotChangedException  {
        if (label.isEmpty())
            throw new EmptyAttributeException("changeLabel", admissionExamLabel);
        if (admissionExamLabel.getLabel().equals(label))
            throw new NotChangedException(admissionExamLabel);

        admissionExamLabel.setLabel(label);
        try (AdmissionExamLabelDAO admissionExamLabelDAO = new AdmissionExamLabelDAO()) {
            admissionExamLabelDAO.update(admissionExamLabel);
        }
    }
}

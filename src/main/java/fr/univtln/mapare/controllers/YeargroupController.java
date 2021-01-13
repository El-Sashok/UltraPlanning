package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.YeargroupDAO;
import fr.univtln.mapare.entities.Yeargroup;

import java.sql.SQLException;

public abstract class YeargroupController {

    private YeargroupController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger toutes les promotions
     * @throws SQLException Exception SQL
     */
    public static void loadYeargroup() throws SQLException {
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.findAll();
        }
    }

    /**
     * Permet de Supprimer un promotion
     * @param yeargroup Promotion à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(Yeargroup yeargroup) throws SQLException {
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.remove(yeargroup);
        }
        Yeargroup.popYeargroupInList(yeargroup);
    }

}

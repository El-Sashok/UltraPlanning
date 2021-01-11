package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.YeargroupDAO;
import fr.univtln.mapare.entities.Yeargroup;

import java.sql.SQLException;

public abstract class YeargroupController {

    private YeargroupController() {}

    public static void loadYeargroup() throws SQLException {
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.findAll();
        }
    }

    public static void remove(Yeargroup yeargroup) throws SQLException {
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.remove(yeargroup);
        }
        Yeargroup.popYeargroupInList(yeargroup);
    }

}

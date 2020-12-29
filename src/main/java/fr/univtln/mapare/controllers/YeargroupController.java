package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.YeargroupDAO;
import fr.univtln.mapare.entities.Yeargroup;

import java.sql.SQLException;

public class YeargroupController {

    public static void loadYeargroup() throws SQLException {
        YeargroupDAO yeargroupDAO = new YeargroupDAO();
        yeargroupDAO.findAll();
        yeargroupDAO.close();
    }

    public static void remove(Yeargroup yeargroup) throws SQLException {
        YeargroupDAO yeargroupDAO = new YeargroupDAO();
        yeargroupDAO.remove(yeargroup);
        yeargroupDAO.close();
        Yeargroup.popYeargroupInList(yeargroup);
    }

}

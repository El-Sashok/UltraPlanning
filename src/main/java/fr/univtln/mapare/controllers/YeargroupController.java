package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.YeargroupDAO;
import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Yeargroup;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.util.List;

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

    /**
     * Permet de créer une nouvelle promotion
     * @param label Le nom de la promotion
     * @throws SQLException Exception SQL
     */
    public static void createYeargroup(String label) throws SQLException {
        Yeargroup yeargroup = new Yeargroup(-1, label);
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.persist(yeargroup);
        }
    }

    /**
     * Permet de changer les groupes de la promotion
     * @param yeargroup La promotion
     * @param groups La nouvelle liste de groupes de la promotion
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException groups ne contient pas de groupe
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeGroups(Yeargroup yeargroup, List<Group> groups) throws SQLException, EmptyAttributeException, NotChangedException {
        if (groups.size() == 0) // if groups is empty, throw an exception
            throw new EmptyAttributeException("changeGroups", yeargroup);
        if (groups.size() == yeargroup.getGroups().size())
            if (groups.containsAll(yeargroup.getGroups())) // if they are the same, throw an exception
                throw new NotChangedException(yeargroup);

        yeargroup.setGroups(groups);
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.updateGroups(yeargroup);
        }
    }

    /**
     * Permet de changer le nom de la promotion
     * @param yeargroup La promotion
     * @param label Le nouveau nom de la promotion
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException label est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeLabel(Yeargroup yeargroup, String label) throws SQLException, EmptyAttributeException, NotChangedException {
        if (label.isEmpty())
            throw new EmptyAttributeException("changeLabel", yeargroup);
        if (yeargroup.getLabel().equals(label))
            throw new NotChangedException(yeargroup);

        yeargroup.setLabel(label);
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroupDAO.update(yeargroup);
        }
    }
}

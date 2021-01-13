package fr.univtln.mapare.controllers;


import fr.univtln.mapare.daos.GroupDAO;
import fr.univtln.mapare.entities.Group;

import java.sql.SQLException;

public abstract class GroupController {

    private GroupController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les groupes
     * @throws SQLException Exception SQL
     */
    public static void loadGroups() throws SQLException {
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.findAll();
        }
    }

    /**
     * Permet de supprimer un groupe
     * @param group Le groupe à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(Group group) throws SQLException {
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.remove(group.getId());
        }
        Group.popGroupInList(group);
    }
    /**
     * Permet de créer un groupe
     * @param label Le nom groupe à créer
     * @throws SQLException Exception SQL
     */
    public static void createGroup(String label) throws SQLException {
        Group group = new Group(-1, label);
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.persist(group);
        }
    }
}

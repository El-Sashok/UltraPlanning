package fr.univtln.mapare.controllers;


import fr.univtln.mapare.daos.GroupDAO;
import fr.univtln.mapare.entities.Group;

import java.sql.SQLException;

public abstract class GroupController {

    private GroupController() {}

    public static void loadGroups() throws SQLException {
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.findAll();
        }
    }

    public static void remove(Group group) throws SQLException {
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.remove(group.getId());
        }
        Group.popGroupInList(group);
    }

    public static void createGroup(String label) throws SQLException {
        Group group = new Group(-1, label);
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.persist(group);
        }
    }
}

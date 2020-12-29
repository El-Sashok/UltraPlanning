package fr.univtln.mapare.controllers;


import fr.univtln.mapare.daos.GroupDAO;
import fr.univtln.mapare.entities.Group;

import java.sql.SQLException;

public abstract class GroupController {

    public static void loadGroups() throws SQLException {
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.findAll();
        groupDAO.close();
    }

    public static void remove(Group group) throws SQLException {
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.remove(group.getId());
        groupDAO.close();
        Group.popGroupInList(group);
    }

    public static void createGroup(String label) throws SQLException {
        Group group = new Group(-1, label);
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.persist(group);
        groupDAO.close();
    }
}

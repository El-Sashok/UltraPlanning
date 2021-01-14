package fr.univtln.mapare.controllers;


import fr.univtln.mapare.daos.GroupDAO;
import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Student;
import fr.univtln.mapare.entities.Yeargroup;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * @param yeargroup Le nom de la promotion auquel appartient le groupe
     * @param label Le nom du groupe
     * @throws SQLException Exception SQL
     */
    public static void createGroup(Yeargroup yeargroup, String label) throws SQLException {
        Group group = new Group(-1, label);
        try (GroupDAO groupDAO = new GroupDAO()) {
            Group newGroup = groupDAO.persist(group);

            List<Group> groups = new ArrayList<>(yeargroup.getGroups());
            groups.add(newGroup);
            YeargroupController.changeGroups(yeargroup, groups);
        }
    }

    /**
     * Permet de changer les groupes de la promotion
     * @param group Le groupe
     * @param students La nouvelle liste d'étudiants du groupe
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException students ne contient pas d'étudiant
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeStudents(Group group, List<Student> students) throws SQLException, EmptyAttributeException, NotChangedException {
        if (students.size() == 0) // if students is empty, throw an exception
            throw new EmptyAttributeException("changeStudents", group);
        if (students.size() == group.getStudents().size())
            if (students.containsAll(group.getStudents())) // if they are the same, throw an exception
                throw new NotChangedException(group);

        group.setStudents(students);
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.updateMembers(group);
        }
    }

    /**
     * Permet de changer le nom du groupe
     * @param group Le groupe
     * @param label Le nouveau nom du groupe
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException label est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeLabel(Group group, String label) throws SQLException, EmptyAttributeException, NotChangedException {
        if (label.isEmpty())
            throw new EmptyAttributeException("changeLabel", group);
        if (group.getLabel().equals(label))
            throw new NotChangedException(group);

        group.setLabel(label);
        try (GroupDAO groupDAO = new GroupDAO()) {
            groupDAO.update(group);
        }
    }
}

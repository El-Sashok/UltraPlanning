package fr.univtln.mapare.exceptions.timebreakexceptions;

import fr.univtln.mapare.entities.Teacher;

public class ManagerTimeBreakException extends Exception {
    public ManagerTimeBreakException(Teacher manager) {
        super("Teacher \"" + manager.getFirstName() + " " + manager.getLastName() + "\" is already busy during this time");
    }
}

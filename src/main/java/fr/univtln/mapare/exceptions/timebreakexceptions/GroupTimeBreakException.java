package fr.univtln.mapare.exceptions.timebreakexceptions;

import fr.univtln.mapare.entities.Group;

public class GroupTimeBreakException extends Exception {
    public GroupTimeBreakException(Group group) {
        super("Group \"" + group.getLabel() + "\" is already busy during this time");
    }
}

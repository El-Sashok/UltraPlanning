package fr.univtln.mapare.exceptions.TimeBreakExceptions;

import fr.univtln.mapare.entities.Group;

public class GroupTimeBreakException extends RuntimeException {
    public GroupTimeBreakException(Group group) {
        super("Group \"" + group.getLabel() + "\" is already busy during this time");
    }
}

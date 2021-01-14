package fr.univtln.mapare.exceptions;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Teacher;

public class BadPracticesException extends Exception {
    public BadPracticesException(Group group) {
        super("Le groupe \"" + group.getLabel() + "\" à déjà trop d'heure dans la journée");
    }

    public BadPracticesException(Teacher manager) {
        super("L'Enseignant \"" + manager.getFirstName() + manager.getLastName() + "\" à déjà trop d'heure dans la journée");
    }
}

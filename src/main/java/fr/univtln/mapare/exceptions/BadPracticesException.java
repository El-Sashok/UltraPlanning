package fr.univtln.mapare.exceptions;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.entities.Teacher;

public class BadPracticesException extends Exception {

    public BadPracticesException(String message) {
        super(message);
    }

    public BadPracticesException(Group group, Module module) {
        super("Le groupe \"" + group.getLabel() + "\" à déjà eu le module \"" + module.getLabel() +"\" toute la semaine dernière");
    }

    public BadPracticesException(String key, Group group) throws BadPracticesException {
        switch (key){
            case "TO_MANY_HOURS":
                throw new BadPracticesException("Le groupe \"" + group.getLabel() + "\" à déjà trop d'heure dans la journée");
            case "AFTER_SIX":
                throw new BadPracticesException("Le groupe \"" + group.getLabel() + "\" termine après 18h toute la semaine");
            default:
                throw new RuntimeException("Invalid key");
        }

    }

    public BadPracticesException(Teacher manager) {
        super("L'Enseignant \"" + manager.getFirstName() + manager.getLastName() + "\" à déjà trop d'heure dans la journée");
    }
}

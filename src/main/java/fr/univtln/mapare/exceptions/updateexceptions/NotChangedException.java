package fr.univtln.mapare.exceptions.updateexceptions;

import fr.univtln.mapare.entities.Entity;

public class NotChangedException extends Exception {
    public NotChangedException(Entity e) {
        super("Entity \"" + e + "\" not changed");
    }
}
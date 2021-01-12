package fr.univtln.mapare.exceptions;

import fr.univtln.mapare.entities.Entity;

public class NotChangedException extends RuntimeException {
    public NotChangedException(Entity e) {
        super("Entity \"" + e + "\" not changed");
    }
}

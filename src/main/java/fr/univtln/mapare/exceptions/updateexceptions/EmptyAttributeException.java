package fr.univtln.mapare.exceptions.updateexceptions;

import fr.univtln.mapare.entities.Entity;

public class EmptyAttributeException extends RuntimeException{
    public EmptyAttributeException(String calledMethod, Entity entity) {
        super("Entity \"" + entity + "\" will have an empty attribute by calling \"" + calledMethod + "\" method");
    }
}

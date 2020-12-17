package fr.univtln.mapare.exceptions;

public class NotFoundException extends DataAccessException {
    public NotFoundException() {
        super("Entity not found");
    }
}

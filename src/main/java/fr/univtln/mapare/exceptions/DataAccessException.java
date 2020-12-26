package fr.univtln.mapare.exceptions;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String localizedMessage) {
        super(localizedMessage);
    }
}

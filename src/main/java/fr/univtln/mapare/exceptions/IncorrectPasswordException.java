package fr.univtln.mapare.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String user) {
        super("Incorrect password for user \"" + user + "\"");
    }
}

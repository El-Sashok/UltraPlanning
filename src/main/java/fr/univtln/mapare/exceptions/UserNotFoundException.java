package fr.univtln.mapare.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String user) {
        super("User \"" + user + "\" not found");
    }
}

package fr.univtln.mapare.exceptions;

import fr.univtln.mapare.entities.Group;

public class UserAlreadyCreatedException extends Exception {
    public UserAlreadyCreatedException(String login) {
        super("Un utilisateur avec le login \"" + login + "\" existe déjà dans la base de donnée");
    }
}

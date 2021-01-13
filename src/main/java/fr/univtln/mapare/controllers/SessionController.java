package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.SessionDAO;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import fr.univtln.mapare.exceptions.UserNotFoundException;

import java.math.BigInteger;
import java.security.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public abstract class SessionController {

    private SessionController() {}

    /**
     * Génère l'haché d'un mot de passe
     * @param typedPassword Mot de passe
     * @return un haché du mot de passe
     * @throws NoSuchAlgorithmException Exception : aucun algorithme de ce type
     */
    private static String hashPassword(String typedPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(typedPassword.getBytes());
        byte[] digest = md.digest();
        return new BigInteger(1, digest).toString(16);
    }

    /**
     * Fonction de connection : Permet de se connecter avec un login / mot de passe
     * @param login Adresse e-mail de connexion
     * @param typedPassword Mot de passe de l'utilisateur
     * @throws SQLException Exception SQL
     * @throws NoSuchAlgorithmException Exception : aucun algorithme de ce type
     */
    public static void login(String login, String typedPassword) throws SQLException, NoSuchAlgorithmException {
        Map<String, Optional<String>> dbInfo;
        try (SessionDAO sessionDAO = new SessionDAO()) {
            dbInfo = sessionDAO.getUserInfo(login);

            if (!dbInfo.isEmpty()) {
                if (dbInfo.get("PASSWORD").get().equals(hashPassword(typedPassword))) {
                    Session.login(Long.valueOf(dbInfo.get("ID").get()),
                            dbInfo.get("LOGIN").get(),
                            dbInfo.get("PASSWORD").get(),
                            Session.Status.valueOf(dbInfo.get("STATUS").get()));
                } else {
                    throw new IncorrectPasswordException(login);
                }
            } else {
                throw new UserNotFoundException(login);
            }
        }
    }

    public static void changePassword(String newPassword) throws NoSuchAlgorithmException, SQLException {
        Session.setHashedPassword(hashPassword(newPassword));
        try (SessionDAO sDAO = new SessionDAO()) {
            sDAO.update(Session.getInstance());
        }
    }

    public static void checkPassword(String password) throws NoSuchAlgorithmException {
        if (!hashPassword(password).equals(Session.getHashedPassword()))
            throw new IncorrectPasswordException(Session.getLogin());
    }

    //TODO Supprimer ceci
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(hashPassword("test"));
        System.out.println(Session.getStatus());
    }
}

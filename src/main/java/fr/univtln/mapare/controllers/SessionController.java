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

    private static String hashPassword(String TypedPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(TypedPassword.getBytes());
        byte[] digest = md.digest();
        return new BigInteger(1, digest).toString(16);
    }

    public static void login(String login, String typedPassword) throws SQLException, NoSuchAlgorithmException {
        SessionDAO sessionDAO = new SessionDAO();
        Map<String, Optional<String>> dbInfo = sessionDAO.getUserInfo(login);
        if (!dbInfo.isEmpty()){
            if (dbInfo.get("PASSWORD").get().equals(hashPassword(typedPassword))){
                new Session(Long.valueOf(dbInfo.get("ID").get()),
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

    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        System.out.println(hashPassword("test"));
        login("sasha@sasha.fr", "tost");
        System.out.println(Session.getStatus());
    }
}

package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.SessionDAO;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import fr.univtln.mapare.exceptions.UserNotFoundException;

import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public abstract class SessionControler {

    private static String hashPassword(String TypedPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(TypedPassword.getBytes());
        byte[]digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    public static void login(String login, String typedPassword) throws SQLException, NoSuchAlgorithmException {
        SessionDAO sessionDAO = new SessionDAO();
        Map<String, Optional<String>> dbInfo = sessionDAO.getUserInfo(login);
        if (dbInfo.get("ID").isPresent()){
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

}

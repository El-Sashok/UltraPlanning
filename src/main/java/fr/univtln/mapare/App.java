package fr.univtln.mapare;

import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.daos.StudentDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.DataAccessException;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws DataAccessException {
        StudentDAO studentDAO = new StudentDAO();
        System.out.println(studentDAO.findAll());

        RoomDAO roomDAO = new RoomDAO();
        System.out.println(roomDAO.findAll());

    }
}

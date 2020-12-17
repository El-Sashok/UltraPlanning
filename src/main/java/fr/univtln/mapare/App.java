package fr.univtln.mapare;

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
        TeacherDAO teacherDAO = new TeacherDAO();

        System.out.println(teacherDAO.findAll());
    }
}

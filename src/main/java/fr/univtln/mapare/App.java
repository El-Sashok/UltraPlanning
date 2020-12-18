package fr.univtln.mapare;

import fr.univtln.mapare.daos.*;
import fr.univtln.mapare.exceptions.DataAccessException;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        System.out.println(teacherDAO.find(1));

        RoomDAO roomDAO = new RoomDAO();
        System.out.println(roomDAO.findAll());

        StudentDAO studentDAO = new StudentDAO();
        System.out.println(studentDAO.findAll());

        GroupDAO groupDAO = new GroupDAO();
        System.out.println(groupDAO.findAll());

        YeargroupDAO yeargroupDAO = new YeargroupDAO();
        System.out.println(yeargroupDAO.findAll());
    }
}
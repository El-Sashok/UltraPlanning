package fr.univtln.mapare;

import fr.univtln.mapare.daos.*;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.DataAccessException;

import java.util.Date;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args ) throws SQLException {
        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.find(1).get();
        roomDAO.close();

        ModuleDAO moduleDAO = new ModuleDAO();
        Module module = moduleDAO.find(1).get();
        moduleDAO.close();

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.find(2).get();
        groupDAO.close();

        LessonDAO lessonDAO = new LessonDAO();
        Lesson lesson = new Lesson(1,
                new Date(),
                new Date(),
                "test label",
                "test memo",
                Reservation.State.NP,
                room,
                Lesson.Type.CM);

        lesson.addModule(module);
        lesson.addGroup(group);

        lessonDAO.persist(lesson);
        lessonDAO.close();

    }


}
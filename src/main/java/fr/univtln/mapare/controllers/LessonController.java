package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.LessonDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class LessonController {

    public static void createLesson(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, Lesson.Type type, List<Module> modules, List<Group> groups, List<Teacher> managers) throws SQLException {
        Lesson lesson = new Lesson(-1, startDate, endDate, label, memo, state, room, type);
        for (Module m : modules) lesson.addModule(m);
        for (Group g : groups) lesson.addGroup(g);
        for (Teacher t : managers) lesson.addTeacher(t);
        LessonDAO lessonDAO = new LessonDAO();
        lessonDAO.persist(lesson);
        lessonDAO.close();
    }
}

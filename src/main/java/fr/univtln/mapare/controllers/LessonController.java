package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.LessonDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class LessonController {

    public static void createLesson(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                    Reservation.State state, Room room, Lesson.Type type, List<Module> modules,
                                    List<Group> groups, List<Teacher> managers) throws SQLException, ManagerTimeBreakException, RoomTimeBreakException, GroupTimeBreakException, StudentTimeBreakException {

        for (Reservation r : Reservation.getReservationList()){
            if (r.getState() == Reservation.State.NP) {
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)){
                    for (Teacher dbTeacher : r.getManagers())
                        for (Teacher LocalTeacher : managers)
                            if (dbTeacher.getId() == LocalTeacher.getId())
                                throw new ManagerTimeBreakException(LocalTeacher);
                    if (r.getRoom().getId() == room.getId())
                        throw new RoomTimeBreakException(room);
                    else if (r instanceof Lesson)
                        for (Group dbGroup : ((Lesson) r).getGroups())
                            for (Group LocalGroup : groups)
                                if (dbGroup.getId() == LocalGroup.getId())
                                    throw new GroupTimeBreakException(LocalGroup);
                    else if (r instanceof Defence)
                        for (Group g : groups)
                            for (Student s : g.getStudents())
                                if (s.getId() == ((Defence) r).getStudent().getId())
                                    throw new StudentTimeBreakException(s);
                }
            }
        }

        Lesson lesson = new Lesson(-1, startDate, endDate, label, memo, state, room, type);
        for (Module m : modules) lesson.addModule(m);
        for (Group g : groups) lesson.addGroup(g);
        for (Teacher t : managers) lesson.addTeacher(t);
        LessonDAO lessonDAO = new LessonDAO();
        lessonDAO.persist(lesson);
        lessonDAO.close();
    }
}

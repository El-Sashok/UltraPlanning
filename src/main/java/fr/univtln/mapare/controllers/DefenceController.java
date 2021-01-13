package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class DefenceController {

    private DefenceController() {}

    public static void createDefence(Reservation newRes, Student student, List<Teacher> managers) throws SQLException,
            RoomTimeBreakException, ManagerTimeBreakException, StudentTimeBreakException {
        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && Controllers.checkTimeBreak(r, newRes)) {
                if (newRes.getRoom().equals(r.getRoom()))
                    throw new RoomTimeBreakException(newRes.getRoom());
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
                if (r instanceof Defence && student.equals(((Defence) r).getStudent()))
                    throw new StudentTimeBreakException(((Defence) r).getStudent());
                if (r instanceof Lesson)
                    for (Group g : ((Lesson) r).getGroups()) {
                        if (g.getStudents().contains(student))
                            throw new StudentTimeBreakException(student);
                    }
                else if (r instanceof AdmissionExam) {
                    if (((AdmissionExam) r).getStudents().contains(student))
                        throw new StudentTimeBreakException(student);
                }
            }
        }

        Defence defence = new Defence(newRes, student);
        defence.setManagers(managers);
        try(DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.persist(defence);
        }
    }
}

package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AdmissionExamController {

    private AdmissionExamController() {}

    public static void createAdmissionExam(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                           Reservation.State state, Room room, AdmissionExamLabel examLabel,
                                           List<Teacher> managers, List<Student> students) throws SQLException,
            ManagerTimeBreakException, RoomTimeBreakException, StudentTimeBreakException {
        for (Teacher t: managers)
            for (Constraint c : t.getConstraints())
                ConstraintController.checkConflicts(startDate, endDate, c);

        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                if (room.equals(r.getRoom()))
                    throw new RoomTimeBreakException(room);
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
                if (r instanceof Defence && students.contains(((Defence) r).getStudent()))
                    throw new StudentTimeBreakException(((Defence) r).getStudent());
                if (r instanceof Lesson)
                    for (Student s : students) {
                        for (Group g : ((Lesson) r).getGroups()) {
                            if (g.getStudents().contains(s))
                                throw new StudentTimeBreakException(s);
                        }
                    }
                else if (r instanceof AdmissionExam) {
                    for (Student s : students) {
                        if (((AdmissionExam) r).getStudents().contains(s))
                            throw new StudentTimeBreakException(s);
                    }
                }
            }
        }

        AdmissionExam aem = new AdmissionExam(-1, startDate, endDate, label, memo, state, room, examLabel);
        aem.setManagers(managers);
        aem.setStudents(students);
        try (AdmissionExamDAO aemDAO = new AdmissionExamDAO()) {
            aemDAO.persist(aem);
        }
    }
}

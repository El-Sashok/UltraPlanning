package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;

import java.sql.SQLException;
import java.util.List;

public abstract class AdmissionExamController {

    private AdmissionExamController() {}

    public static void createAdmissionExam(Reservation newRes, AdmissionExamLabel label, List<Teacher> managers,
                                           List<Student> students) throws SQLException, ManagerTimeBreakException, RoomTimeBreakException, StudentTimeBreakException {
        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && Controllers.checkTimeBreak(r, newRes)) {
                if (newRes.getRoom().equals(r.getRoom()))
                    throw new RoomTimeBreakException(newRes.getRoom());
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

        AdmissionExam aem = new AdmissionExam(newRes, label);
        aem.setManagers(managers);
        aem.setStudents(students);
        try (AdmissionExamDAO aemDAO = new AdmissionExamDAO()) {
            aemDAO.persist(aem);
        }
    }
}

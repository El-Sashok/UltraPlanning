package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ExamBoardDAO;
import fr.univtln.mapare.entities.ExamBoard;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.entities.Yeargroup;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;

import java.sql.SQLException;
import java.util.List;

public abstract class ExamBoardController {

    private ExamBoardController() {}

    public static void createExamBoard(Reservation reservation, Yeargroup yg, List<Teacher> managers)
            throws SQLException, RoomTimeBreakException, ManagerTimeBreakException {
        for (Reservation r : Reservation.getReservationList())
            if (r.isNP() && Controllers.checkTimeBreak(r, reservation)) {
                if (reservation.getRoom().equals(r.getRoom()))
                    throw new RoomTimeBreakException(reservation.getRoom());
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
            }

        ExamBoard examBoard = new ExamBoard(reservation, yg);
        examBoard.setManagers(managers);
        try (ExamBoardDAO ebDAO = new ExamBoardDAO()) {
            ebDAO.persist(examBoard);
        }
    }
}

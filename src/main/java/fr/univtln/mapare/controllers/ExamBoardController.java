package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ExamBoardDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class ExamBoardController {

    private ExamBoardController() {}

    public static void createExamBoard(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                       Reservation.State state, Room room, Yeargroup yg, List<Teacher> managers)
            throws SQLException, RoomTimeBreakException, ManagerTimeBreakException {
        for (Teacher t: managers)
            for (Constraint c : t.getConstraints())
                ConstraintController.checkConflicts(startDate, endDate, c);

        for (Reservation r : Reservation.getReservationList())
            if (r.isNP() && Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                if (room.equals(r.getRoom()))
                    throw new RoomTimeBreakException(room);
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
            }

        ExamBoard examBoard = new ExamBoard(-1, startDate, endDate, label, memo, state, room, yg);
        examBoard.setManagers(managers);
        try (ExamBoardDAO ebDAO = new ExamBoardDAO()) {
            ebDAO.persist(examBoard);
        }
    }
}

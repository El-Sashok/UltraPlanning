package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ExamBoardDAO;
import fr.univtln.mapare.entities.ExamBoard;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.entities.Yeargroup;

import java.sql.SQLException;
import java.util.List;

public abstract class ExamBoardController {

    private ExamBoardController() {}

    public static void createExamBoard(Reservation reservation, Yeargroup yg, List<Teacher> managers) throws SQLException {
        ExamBoard examBoard = new ExamBoard(reservation, yg);
        examBoard.setManagers(managers);
        try (ExamBoardDAO ebDAO = new ExamBoardDAO()) {
            ebDAO.persist(examBoard);
        }
    }
}

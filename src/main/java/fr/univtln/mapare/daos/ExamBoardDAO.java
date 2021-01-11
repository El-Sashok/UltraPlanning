package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.ExamBoard;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Yeargroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamBoardDAO extends AbstractDAO<ExamBoard> {

    public ExamBoardDAO() throws SQLException {
        super("INSERT INTO EXAMBOARD(ID, YEARGROUP) VALUES(?,?)",
                "UPDATE EXAMBOARD SET ID=?, YEARGROUP=? WHERE ID=?");
    }

    @Override
    protected ExamBoard fromResultSet(ResultSet resultSet) throws SQLException {
        for (Reservation r: Reservation.getReservationList()) {
            if (r.getId() == resultSet.getLong("ID") && r instanceof ExamBoard)
                return (ExamBoard) r;
        }

        Reservation reservation;
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservation = reservationDAO.find(resultSet.getLong("ID")).get();
            Reservation.popReservationList(reservation);
        }

        Yeargroup yeargroup;
        try (YeargroupDAO yeargroupDAO = new YeargroupDAO()) {
            yeargroup = yeargroupDAO.find(resultSet.getLong("YEARGROUP")).get();
        }

        return new ExamBoard(reservation, yeargroup);
    }

    @Override
    public ExamBoard persist(ExamBoard examBoard) throws SQLException {
        Reservation r;
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            r = reservationDAO.persist(examBoard, "EXAM_BOARD");
        }
        Reservation.popReservationList(r);

        examBoard.setId(r.getId());
        populate(persistPS, examBoard);
        return super.persist();
    }

    @Override
    public void update(ExamBoard examBoard) throws SQLException {
        populate(updatePS, examBoard);
        updatePS.setLong(3, examBoard.getId());
        super.persist();
    }

    private void populate(PreparedStatement popPS, ExamBoard examBoard) throws SQLException {
        popPS.setLong(1, examBoard.getId());
        popPS.setLong(2, examBoard.getYeargroup().getId());
    }

    @Override
    public String getTableName() {
        return "EXAMBOARD";
    }
}

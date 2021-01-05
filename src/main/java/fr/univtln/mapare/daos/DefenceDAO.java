package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Defence;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefenceDAO extends AbstractDAO<Defence> {
    public DefenceDAO() throws SQLException {
        super("INSERT INTO DEFENCE(ID, STUDENT) VALUES (?,?)",
                "UPDATE DEFENCE SET ID=?, STUDENT=? WHERE ID=?");
    }

    @Override
    protected Defence fromResultSet(ResultSet resultSet) throws SQLException {

        for (Reservation r: Reservation.getReservationList()) {
            if (r.getId() == resultSet.getLong("ID") && r instanceof Defence)
                return (Defence) r;
        }

        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = reservationDAO.find(resultSet.getLong("ID")).get();
        Reservation.popReservationList(reservation);
        reservationDAO.close();

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.find(resultSet.getLong("STUDENT")).get();
        studentDAO.close();

        return new Defence(reservation, student);
    }

    @Override
    public Defence persist(Defence defence) throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation r = reservationDAO.persist(defence,"DEFENCE");
        reservationDAO.close();
        Reservation.popReservationList(r);

        defence.setId(r.getId());
        populate(persistPS, defence);
        return super.persist();
    }

    @Override
    public void update(Defence defence) throws SQLException {
        populate(updatePS, defence);
        updatePS.setLong(3, defence.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Defence defence) throws SQLException {
        popPS.setLong(1, defence.getId());
        popPS.setLong(2, defence.getStudent().getId());
    }

    @Override
    public String getTableName() {
        return "DEFENCE";
    }
}

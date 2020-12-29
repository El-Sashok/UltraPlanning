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
        for (Defence d: Defence.getDefenceList()) {
            if (d.getId() == resultSet.getLong("ID"))
                return d;
        }
        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = reservationDAO.find(resultSet.getLong("ID")).get();
        reservationDAO.close();
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.find(resultSet.getLong("STUDENT")).get();
        studentDAO.close();

        return new Defence(reservation, student);
    }

    @Override
    public Defence persist(Defence defence) throws SQLException {
        Reservation reservation = new Reservation(defence.getId(),
                defence.getStartDate(),
                defence.getEndDate(),
                defence.getLabel(),
                defence.getMemo(),
                defence.getState(),
                defence.getRoom());
        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation r = reservationDAO.persist(reservation,"DEFENCE");
        reservationDAO.close();
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

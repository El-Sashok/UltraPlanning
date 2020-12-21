package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log
public class ReservationDAO extends AbstractDAO<Reservation> {
    private final PreparedStatement findManagersPS;
    private final PreparedStatement persistManagerPS;
    private final PreparedStatement updateManagerPS;
    RoomDAO roomDAO;

    public ReservationDAO() throws SQLException {
        super("INSERT INTO RESERVATION(START, END, LABEL, MEMO, TYPE, ROOM, STATE) VALUES (?,?,?,?,?,?,?)",
                "UPDATE RESERVATION SET START=?, END=?, LABEL=?, MEMO=?, TYPE=?, ROOM=?, STATE=? WHERE ID=?");
        this.roomDAO = new RoomDAO();

        findManagersPS = connection.prepareStatement("SELECT * FROM MANAGERS WHERE RESERVATION=?");
        persistManagerPS = connection.prepareStatement("INSERT INTO MANAGERS(RESERVATION, MANAGER) VALUES (?,?)");
        updateManagerPS = connection.prepareStatement("UPDATE MANAGERS SET RESERVATION=?, MANAGER=? WHERE ID=?");
    }

    @Override
    protected Reservation fromResultSet(ResultSet resultSet) throws SQLException {
        Room room = roomDAO.find(resultSet.getLong("ROOM")).get();
        return new Reservation(resultSet.getLong("ID"),
                resultSet.getDate("START"),
                resultSet.getDate("END"),
                resultSet.getString("LABEL"),
                resultSet.getString("MEMO"),
                Reservation.State.valueOf(resultSet.getString("STATE")),
                room);
    }

    protected Reservation fromResultSet(ResultSet resultSet, List<Teacher> managers) throws SQLException, DataAccessException {
        Room room = roomDAO.find(resultSet.getLong("ROOM")).get();
        Reservation reservation = new Reservation(resultSet.getLong("ID"),
                resultSet.getDate("START"),
                resultSet.getDate("END"),
                resultSet.getString("LABEL"),
                resultSet.getString("MEMO"),
                Reservation.State.valueOf(resultSet.getString("STATE")),
                room);

        for (Teacher t: managers) {
            reservation.addTeacher(t);
        }
        return reservation;
    }

    @Override
    public Reservation persist(Reservation reservation) throws SQLException {
        populate(persistPS, reservation);
        return super.persist();
    }

    public Reservation persist(Reservation reservation, String type) throws SQLException {
        populate(persistPS, reservation, type);
        return super.persist();
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        populate(updatePS, reservation);
        updatePS.setLong(8, reservation.getId());

        super.update();
    }

    private void populate(PreparedStatement popPS, Reservation reservation) throws SQLException {
        populate(popPS, reservation, null);
    }

    private void populate(PreparedStatement popPS, Reservation reservation, String type) throws SQLException {
        popPS.setDate(1, new java.sql.Date(reservation.getStartDate().getTime()));
        popPS.setDate(2, new java.sql.Date(reservation.getEndDate().getTime()));
        popPS.setString(3, reservation.getLabel());
        popPS.setString(4, reservation.getMemo());
        popPS.setString(5, type);
        popPS.setLong(6,reservation.getRoom().getId());
        popPS.setString(7, reservation.getState().toString());
    }

    @Override
    public String getTableName() {
        return "RESERVATION";
    }
}

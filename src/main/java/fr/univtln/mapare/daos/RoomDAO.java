package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class RoomDAO extends AbstractDAO<Room> {

    public RoomDAO() throws SQLException {
        super("",
                "");
    }

    @Override
    protected Room fromResultSet(ResultSet resultSet) throws SQLException {
        return new Room(resultSet.getInt("ID"),
                resultSet.getString("BUILDING"),
                resultSet.getInt("NUMBER"),
                resultSet.getInt("CAPACITY"),
                resultSet.getString("LABEL"),
                resultSet.getString("INFO"));

    }

    @Override
    public Room persist(Room room) {
        return null;
    }

    @Override
    public void update(Room room) {

    }

    @Override
    public String getTableName() {
        return "ROOM";
    }
}

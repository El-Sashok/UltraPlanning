package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Room;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class RoomDAO extends AbstractDAO<Room> {

    public RoomDAO() throws SQLException {
        super("INSERT INTO ROOM(BUILDING, NUMBER, CAPACITY, LABEL, INFO) VALUES(?,?,?,?,?)",
                "UPDATE ROOM SET BUILDING=?, NUMBER=?, CAPACITY=?, LABEL=?, INFO=? WHERE ID=?");
    }

    @Override
    protected Room fromResultSet(ResultSet resultSet) throws SQLException {
        for (Room r: Room.getRoomList()) {
            if (r.getId() == resultSet.getLong("ID"))
                return r;
        }
        return new Room(resultSet.getInt("ID"),
                resultSet.getString("BUILDING"),
                resultSet.getInt("NUMBER"),
                resultSet.getInt("CAPACITY"),
                resultSet.getString("LABEL"),
                resultSet.getString("INFO"));

    }

    @Override
    public Room persist(Room room) throws SQLException {
        populate(persistPS, room);
        return super.persist();
    }

    @Override
    public void update(Room room) throws SQLException {
        populate(updatePS, room);
        updatePS.setLong(6, room.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Room room) throws SQLException {
        popPS.setString(1, room.getBuilding());
        popPS.setInt(2, room.getNumber());
        popPS.setInt(3, room.getCapacity());
        popPS.setString(4, room.getLabel());
        popPS.setString(5, room.getInfo());
    }

    @Override
    public String getTableName() {
        return "ROOM";
    }
}

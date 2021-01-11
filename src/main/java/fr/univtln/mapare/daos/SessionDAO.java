package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Session;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Log
public class SessionDAO extends AbstractDAO<Session> {

    public SessionDAO() throws SQLException {
        super("INSERT INTO SESSION(LOGIN, PASSWORD, STATUS) VALUES (?,?,?)",
                "UPDATE SESSION SET LOGIN=?, PASSWORD=?, STATUS=? WHERE ID=?");
    }

    @Override
    protected Session fromResultSet(ResultSet resultSet) throws SQLException {
        return new Session(resultSet.getLong("ID"),
                resultSet.getString("LOGIN"),
                resultSet.getString("PASSWORD"),
                Session.Status.valueOf(resultSet.getString("STATUS")));
    }

    @Override
    public Session persist() throws SQLException { return persist((Session) null); }

    @Override
    public Session persist(Session session) throws SQLException {
        populate(persistPS);
        return super.persist();
    }

    @Override
    public void update() throws SQLException { update(null); }

    @Override
    public void update(Session session) throws SQLException {
        populate(updatePS);
        updatePS.setLong(4, Session.getInstance().getId());
        super.update();
    }

    private void populate(PreparedStatement popPS) throws SQLException {
        popPS.setString(1, Session.getLogin());
        popPS.setString(2, Session.getHashedPassword());
        popPS.setString(3, Session.getStatus().toString());
    }

    public Map<String, Optional<String>> getUserInfo(String userLogin) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + getTableName() + " WHERE LOGIN='" + userLogin+"'");
        Map <String, Optional<String>> info = new HashMap<>();
        if (rs.next()) {
            info.put("ID", Optional.ofNullable(rs.getString("ID")));
            info.put("LOGIN", Optional.ofNullable(rs.getString("LOGIN")));
            info.put("PASSWORD", Optional.ofNullable(rs.getString("PASSWORD")));
            info.put("STATUS", Optional.ofNullable(rs.getString("STATUS")));
        }
        return info;
    }

    @Override
    public String getTableName() {
        return "SESSION";
    }
}

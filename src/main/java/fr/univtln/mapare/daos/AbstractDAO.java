package fr.univtln.mapare.daos;

import fr.univtln.mapare.datasources.DBCPDataSource;
import fr.univtln.mapare.entities.Entity;
import fr.univtln.mapare.exceptions.DataAccessException;
import fr.univtln.mapare.exceptions.NotFoundException;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public abstract class AbstractDAO<E extends Entity> implements DAO<E> {
    protected final Connection connection;
    protected final PreparedStatement persistPS;
    protected final PreparedStatement updatePS;
    protected final PreparedStatement findPS;
    protected final PreparedStatement findAllPS;

    public AbstractDAO(String persistPS, String updatePS) throws SQLException {
        this.connection = DBCPDataSource.getConnection();
        this.findPS = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE ID=?");
        this.findAllPS = connection.prepareStatement("SELECT * FROM " + getTableName());
        this.persistPS = connection.prepareStatement(persistPS, Statement.RETURN_GENERATED_KEYS);
        this.updatePS = connection.prepareStatement(updatePS);
        log.info(getTableName() + " DAO Created.");
    }

    public abstract String getTableName();

    protected abstract E fromResultSet(ResultSet resultSet) throws SQLException;

    public Optional<E> find(long id) throws SQLException {
        E entity = null;
        findPS.setLong(1, id);
        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            entity = fromResultSet(rs);
        return Optional.ofNullable(entity);
    }

    public void remove(long id) throws SQLException {
        connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE ID=" + id);
    }

    public void clean() throws SQLException {
        connection.createStatement().execute("DELETE FROM " + getTableName());
    }

    @Override
    public List<E> findAll() throws SQLException {
        List<E> entityList = new ArrayList<>();
        ResultSet rs = findAllPS.executeQuery();
        while (rs.next()) entityList.add(fromResultSet(rs));
        return entityList;
    }

    public E persist() throws SQLException {
        long id = -1;
        persistPS.executeUpdate();
        ResultSet rs = persistPS.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getLong(1);
            log.fine("Generated PK = " + id);
        }
        return find(id).orElseThrow(NotFoundException::new);
    }

    public void update() throws SQLException {
        updatePS.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}

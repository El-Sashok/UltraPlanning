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

    public AbstractDAO(String persistPS, String updatePS) throws DataAccessException {
        Connection _connection = null;
        PreparedStatement _findPS = null, _findAllPS = null, _persistPS = null, _updatePS = null;
        try {
            _connection = DBCPDataSource.getConnection();
            _findPS = _connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE ID=?");
            _findAllPS = _connection.prepareStatement("SELECT * FROM " + getTableName());
            _persistPS = _connection.prepareStatement(persistPS, Statement.RETURN_GENERATED_KEYS);
            _updatePS = _connection.prepareStatement(updatePS);

        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        this.connection = _connection;
        this.findPS = _findPS;
        this.findAllPS = _findAllPS;
        this.persistPS = _persistPS;
        this.updatePS = _updatePS;
        log.warning(getTableName() + " DAO Created.");
    }

    public abstract String getTableName();

    protected abstract E fromResultSet(ResultSet resultSet) throws SQLException;

    public Optional<E> find(long id) throws DataAccessException {
        E entity = null;
        try {
            findPS.setLong(1, id);
            ResultSet rs = findPS.executeQuery();
            while (rs.next())
                entity = fromResultSet(rs);
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return Optional.ofNullable(entity);
    }

    public void remove(long id) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE ID=" + id);
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
    }

    public void clean() throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName());
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
    }

    @Override
    public List<E> findAll() throws DataAccessException {
        List<E> entityList = new ArrayList<>();
        try {
            ResultSet rs = findAllPS.executeQuery();
            while (rs.next()) entityList.add(fromResultSet(rs));
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return entityList;
    }

    public E persist() throws DataAccessException {
        long id = -1;
        try {
            persistPS.executeUpdate();
            ResultSet rs = persistPS.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
                log.fine("Generated PK = " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return find(id).orElseThrow(NotFoundException::new);
    }

    public void update() throws DataAccessException {
        try {
            updatePS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void close() throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }
}

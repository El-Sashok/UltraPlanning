package fr.univtln.mapare.daos;

import fr.univtln.mapare.datasources.DBCPDataSource;
import fr.univtln.mapare.entities.Entity;
import fr.univtln.mapare.exceptions.NotFoundException;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe Abstraite de DAO d'entité
 * @author Emmanuel BRUNO
 * @version 1.0
 */
@Log
public abstract class AbstractDAO<E extends Entity> implements DAO<E> {
    protected final Connection connection;
    protected final PreparedStatement persistPS;
    protected final PreparedStatement updatePS;
    protected final PreparedStatement findPS;
    protected final PreparedStatement findAllPS;

    /**
     * Constructeur de la DAO abstraite
     * @param persistPS String qui permet de générer la PreparedStatement de persistance
     * @param updatePS String qui permet de générer la PreparedStatement de modification
     * @throws SQLException Exception SQL
     */
    protected AbstractDAO(String persistPS, String updatePS) throws SQLException {
        this.connection = DBCPDataSource.getConnection();
        this.findPS = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE ID=?");
        this.findAllPS = connection.prepareStatement("SELECT * FROM " + getTableName());
        this.persistPS = connection.prepareStatement(persistPS, Statement.RETURN_GENERATED_KEYS);
        this.updatePS = connection.prepareStatement(updatePS);
        log.info(getTableName() + " DAO Created.");
    }

    /**
     * @return Le nom de la Table dans la base de donnée associé à la DAO
     */
    public abstract String getTableName();

    /**
     * Permet de créer une entité à partir d'un ResultSet
     * @param resultSet ResultSet qui contient les information pour créer l'entité
     * @return L'entité E définie dans la fonction
     * @throws SQLException Exception SQL
     */
    protected abstract E fromResultSet(ResultSet resultSet) throws SQLException;

    /**
     * Permet de rechercher une entité dans la base de données puis d'en créer une entité
     * @param id L'identifiant de l'entité
     * @return l'entité trouvé dans la base de données
     * @throws SQLException Exception SQL
     */
    public Optional<E> find(long id) throws SQLException {
        E entity = null;
        findPS.setLong(1, id);
        ResultSet rs = findPS.executeQuery();
        while (rs.next()) {
            entity = fromResultSet(rs);
        }
        return Optional.ofNullable(entity);
    }

    /**
     * Permet de supprimer une entité de la base de données
     * @param id L'identifiant de l'entité à supprimer
     * @throws SQLException Exception SQL
     */
    public void remove(long id) throws SQLException {
        connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE ID=" + id);
    }

    /**
     * Permet de vider la table de la base de données
     * @throws SQLException Exception SQL
     */
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

    /**
     * Permet de sauvegarder une entité dans la base de données
     * @return l'identifiant de l'entité sauvegardé, sinon renvoie une exception d'objet non trouvé
     * @throws SQLException Exception SQL
     */
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

    /**
     * permet de mettre à jour une entité dans la base de donnée
     * @throws SQLException Exception SQL
     */
    public void update() throws SQLException {
        updatePS.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        log.info(getTableName() + " DAO Closed.");
    }
}

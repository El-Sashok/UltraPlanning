package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This interface defines the generic method for DAO Objects.
 *
 * @param <E> is the type of entities managed by the DAO
 */
public interface DAO<E extends Entity> extends AutoCloseable {
    /**
     * Finds and returns an entity by id in the database.
     * Note the usage of Optional to avoir Null Pointer Exception (NPE).
     *
     * @param id the id of the entity
     * @return the entity with the given id from the database.
     */
    Optional<E> find(long id) throws SQLException;

    /**
     * Return the complete list of entities managed by the DAO from the database.
     *
     * @return the list of entities managed by the DAO.
     */
    List<E> findAll() throws SQLException;

    /**
     * Persists a new entity in the database.
     *
     * @param e The entity to be persisted.
     * @return The entity or a clone if the id has been updated by the database.
     */
    E persist(E e) throws SQLException;

    /**
     * A default method to persist a list of entities.
     *
     * @param list The list of entities to be persisted.
     * @return The list of entities or clones if their id has been updated by the database.
     */
    default List<E> persist(List<E> list) throws SQLException {
        List<E> resultList = new ArrayList<>();
        for (E e : list) resultList.add(persist(e));
        return resultList;
    }

    /**
     * Update the entity in the database with the parameter.
     *
     * @param e The entity to be updated. The id is used and cannot be updated.
*/
    void update(E e) throws SQLException;

    /**
     * Removes the entity with the given id from the database.
     *
     * @param id the id of the entity to remove
*/
    void remove(long id) throws SQLException;

    /**
     * Removes the given entity from the database using its ID.
     *
     * @param e the entity to be removed.
*/
    default void remove(E e) throws SQLException {
        remove(e.getId());
    }

    /**
     * Removes all the entities managed by the DAO from the database.
     *
*/
    void clean() throws SQLException;

    /**
     * Release the connection to the connection pool
     */
    void close() throws SQLException;

}

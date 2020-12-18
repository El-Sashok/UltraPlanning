package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class ConstraintDAO extends AbstractDAO<Constraint> {

    public ConstraintDAO() throws SQLException {
        super("",
                "");
    }

    @Override
    protected Constraint fromResultSet(ResultSet resultSet) throws SQLException {
        return new Constraint(
                resultSet.getLong("id"),
                resultSet.getDate("START"),
                resultSet.getDate("END"));
    }

    @Override
    public Constraint persist(Constraint constraint) {
        return null;
    }

    @Override
    public void update(Constraint constraint) {

    }

    @Override
    public String getTableName() {
        return "STUDENT";
    }
}

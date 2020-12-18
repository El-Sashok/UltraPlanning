package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class ModuleDAO extends AbstractDAO<Module> {

    public ModuleDAO() throws DataAccessException {
        super("INSERT INTO MODULE(TITLE, NB_HOURS) VALUES (?,?)",
                "UPDATE MODULE SET TITLE=?, NB_HOURS=? WHERE ID=?");
    }

    @Override
    protected Module fromResultSet(ResultSet resultSet) throws SQLException {
        return new Module(resultSet.getInt("ID"),
                resultSet.getString("TITLE"),
                resultSet.getInt("NB_HOURS"));
    }

    @Override
    public Module persist(Module module) throws DataAccessException {
        return persist(module.getLabel(), module.getNbHour());
    }

    public Module persist(final String label, final int nbHour) throws DataAccessException {
        try {
            persistPS.setString(1, label);
            persistPS.setInt(2, nbHour);
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(Module module) throws DataAccessException {
        try {
            updatePS.setString(1, module.getLabel());
            updatePS.setInt(2, module.getNbHour());
            updatePS.setLong(3, module.getId());
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        super.update();
    }

    @Override
    public String getTableName() {
        return "MODULE";
    }
}

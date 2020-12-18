package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import javax.jnlp.PersistenceService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class ModuleDAO extends AbstractDAO<Module> {

    public ModuleDAO() throws SQLException {
        super("INSERT INTO MODULE(TITLE, NB_HOURS) VALUES (?,?)",
                "UPDATE MODULE SET TITLE=?, NB_HOURS=? WHERE ID=?");
    }

    @Override
    protected Module fromResultSet(ResultSet resultSet) throws SQLException {
        Module module = Module.getModuleList().get(resultSet.getInt("ID"));
        if (module != null) {
            return module;
        } else {
            return new Module(resultSet.getInt("ID"),
                    resultSet.getString("TITLE"),
                    resultSet.getInt("NB_HOURS"));
        }
    }

    @Override
    public Module persist(Module module) throws SQLException {
        populate(persistPS, module);
        return super.persist();
    }

    @Override
    public void update(Module module) throws SQLException {
        populate(updatePS, module);
        updatePS.setLong(3, module.getId());
        super.update();
    }

    public void populate(PreparedStatement popPS, Module module) throws SQLException {
        popPS.setString(1, module.getLabel());
        popPS.setInt(2, module.getNbHour());
    }

    @Override
    public String getTableName() {
        return "MODULE";
    }
}

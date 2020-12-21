package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Module;
import lombok.extern.java.Log;

import javax.jnlp.PersistenceService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class ModuleDAO extends AbstractDAO<Module> {

    public ModuleDAO() throws SQLException {
        super("INSERT INTO MODULE(LABEL, NB_HOURS) VALUES (?,?)",
                "UPDATE MODULE SET LABEL=?, NB_HOURS=? WHERE ID=?");
    }

    @Override
    protected Module fromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        for (Module m: Module.getModuleList()) {
            if (m.getId() == resultSet.getInt("ID"))
                return m;
        }
        return new Module(resultSet.getInt("ID"),
                resultSet.getString("LABEL"),
                resultSet.getInt("NB_HOURS"));
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

    private void populate(PreparedStatement popPS, Module module) throws SQLException {
        popPS.setString(1, module.getLabel());
        popPS.setInt(2, module.getNbHour());
    }

    @Override
    public String getTableName() {
        return "MODULE";
    }
}

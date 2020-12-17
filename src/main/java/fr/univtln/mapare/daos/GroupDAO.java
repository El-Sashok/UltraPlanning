package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class GroupDAO extends AbstractDAO<Group>{

    public GroupDAO(String persistPS, String updatePS) throws DataAccessException {
        super("",
                "");
    }

    @Override
    protected Group fromResultSet(ResultSet resultSet) throws SQLException {
        Group group =  new Group(resultSet.getInt("ID"),
            resultSet.getString("LABEL"));
        liste = find("ID");
        group.addStudent()
    }

    @Override
    public Group persist(Group group) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Group group) throws DataAccessException {

    }

    @Override
    public String getTableName() {
        return "GROUP";
    }
}

package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Yeargroup;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class YeargroupDAO extends AbstractDAO<Yeargroup> {
    private final PreparedStatement findYearGroupsPS;
    private final PreparedStatement findGroupPS;
    private final GroupDAO groupDAO;

    public YeargroupDAO() throws SQLException {
        super("", "");
        this.groupDAO = new GroupDAO();

        this.findYearGroupsPS = connection.prepareStatement("SELECT * FROM YEARGROUP_GROUPS WHERE YEARGROUP=?");
        this.findGroupPS = connection.prepareStatement("SELECT * FROM CLASS_GROUP WHERE ID=?");
    }

    @Override
    protected Yeargroup fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Yeargroup fromResultSet(ResultSet resultSet, List<Group> groups) throws SQLException {
        Yeargroup yeargroup = new Yeargroup(resultSet.getInt("ID"),
                resultSet.getString("LABEL"));
        for (Group g : groups) {
            yeargroup.addGroup(g);
        }
        return yeargroup;
    }

    @Override
    public Optional<Yeargroup> find(long id) throws SQLException {
        GroupDAO groupDAO = new GroupDAO();
        Yeargroup yeargroup = null;
        List<Group> groups = new ArrayList<>();
        findPS.setLong(1, id);
        findYearGroupsPS.setLong(1, id);
        ResultSet rs_findPS = findPS.executeQuery();
        ResultSet rs_findYG = findYearGroupsPS.executeQuery();
        while (rs_findYG.next()) {
            findGroupPS.setLong(1, rs_findYG.getInt("CLASS_GROUP"));
            ResultSet rs_findGP = findGroupPS.executeQuery();
            rs_findGP.next();
            groups.add(groupDAO.fromResultSet(rs_findGP, groupDAO.find(rs_findGP.getInt("ID")).get().getStudents()));
        }
        while (rs_findPS.next()) yeargroup = fromResultSet(rs_findPS, groups);

        return Optional.ofNullable(yeargroup);
    }

    @Override
    public List<Yeargroup> findAll() throws SQLException {
        GroupDAO groupDAO = new GroupDAO();
        List<Yeargroup> entityList = new ArrayList<>();
        ResultSet rs_findPS = findAllPS.executeQuery();
        while (rs_findPS.next()) {
            List<Group> groups = new ArrayList<>();
            findYearGroupsPS.setLong(1, rs_findPS.getLong("ID"));
            ResultSet rs_findYG = findYearGroupsPS.executeQuery();
            while (rs_findYG.next()) {
                findGroupPS.setLong(1, rs_findYG.getLong("CLASS_GROUP"));
                ResultSet rs_findGP = findGroupPS.executeQuery();
                rs_findGP.next();
                groups.add(groupDAO.fromResultSet(rs_findGP, groupDAO.find(rs_findGP.getInt("ID")).get().getStudents()));
            }
            entityList.add(fromResultSet(rs_findPS, groups));
        }
        return entityList;
    }

    @Override
    public Yeargroup persist(Yeargroup yeargroup) {
        return null;
    }

    @Override
    public void update(Yeargroup yeargroup) {

    }

    @Override
    public String getTableName() {
        return "YEARGROUP";
    }
}
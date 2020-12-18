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
    private final PreparedStatement findYG;
    private final PreparedStatement findGP;
    private final GroupDAO groupDAO;

    public YeargroupDAO() throws DataAccessException {
        super("", "");
        this.groupDAO = new GroupDAO();
        PreparedStatement _findYG = null;
        try {
            _findYG = connection.prepareStatement("SELECT * FROM YEARGROUP_GROUPS WHERE YEARGROUP=?");
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        this.findYG = _findYG;
        PreparedStatement _findGP = null;
        try {
            _findGP = connection.prepareStatement("SELECT * FROM CLASS_GROUP WHERE ID=?");
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        this.findGP = _findGP;
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
    public Optional<Yeargroup> find(long id) throws DataAccessException {
        GroupDAO groupDAO = new GroupDAO();
        Yeargroup yeargroup = null;
        List<Group> groups = new ArrayList<>();
        try {
            findPS.setLong(1, id);
            findYG.setLong(1, id);
            ResultSet rs_findPS = findPS.executeQuery();
            ResultSet rs_findYG = findYG.executeQuery();
            while (rs_findYG.next()) {
                findGP.setLong(1, rs_findYG.getInt("CLASS_GROUP"));
                ResultSet rs_findGP = findGP.executeQuery();
                rs_findGP.next();
                groups.add(groupDAO.fromResultSet(rs_findGP, groupDAO.find(rs_findGP.getInt("ID")).get().getStudents()));
            }
            while (rs_findPS.next()) yeargroup = fromResultSet(rs_findPS, groups);
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return Optional.ofNullable(yeargroup);
    }

    @Override
    public List<Yeargroup> findAll() throws DataAccessException {
        GroupDAO groupDAO = new GroupDAO();
        List<Yeargroup> entityList = new ArrayList<>();
        try {
            ResultSet rs_findPS = findAllPS.executeQuery();
            while (rs_findPS.next()) {
                List<Group> groups = new ArrayList<>();
                try {
                    findYG.setLong(1, rs_findPS.getLong("ID"));
                    ResultSet rs_findYG = findYG.executeQuery();
                    while (rs_findYG.next()) {
                        findGP.setLong(1, rs_findYG.getLong("CLASS_GROUP"));
                        ResultSet rs_findGP = findGP.executeQuery();
                        rs_findGP.next();
                        groups.add(groupDAO.fromResultSet(rs_findGP, groupDAO.find(rs_findGP.getInt("ID")).get().getStudents()));
                    }
                    entityList.add(fromResultSet(rs_findPS, groups));
                } catch (SQLException e) {
                    throw new DataAccessException(e.getLocalizedMessage());
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return entityList;
    }

    @Override
    public Yeargroup persist(Yeargroup yeargroup) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Yeargroup yeargroup) throws DataAccessException {

    }

    @Override
    public String getTableName() {
        return "YEARGROUP";
    }
}
package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Yeargroup;
import fr.univtln.mapare.exceptions.NotFoundException;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class YeargroupDAO extends AbstractDAO<Yeargroup> {
    private final PreparedStatement findGroupsPS;
    private final PreparedStatement persistGroupPS;
    private final PreparedStatement removeGroupPS;

    public YeargroupDAO() throws SQLException {
        super("INSERT INTO YEARGROUP(LABEL) VALUES (?)",
                "UPDATE YEARGROUP SET LABEL=? WHERE ID=?");
        findGroupsPS = connection.prepareStatement("SELECT * FROM YEARGROUP_GROUPS WHERE YEARGROUP=?");
        persistGroupPS = connection.prepareStatement("INSERT INTO YEARGROUP_GROUPS(YEARGROUP, CLASS_GROUP) VALUES(?,?)");
        removeGroupPS = connection.prepareStatement("DELETE FROM YEARGROUP_GROUPS WHERE ID=?");
    }

    @Override
    protected Yeargroup fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Yeargroup fromResultSet(ResultSet resultSet, List<Group> groups) throws SQLException {
        for (Yeargroup yg: Yeargroup.getYeargroupList()) {
            if (yg.getId() == resultSet.getLong("ID"))
                return yg;
        }
        Yeargroup yeargroup = new Yeargroup(resultSet.getLong("ID"),
                resultSet.getString("LABEL"));
        for (Group g : groups) {
            yeargroup.addGroup(g);
        }
        return yeargroup;
    }

    @Override
    public Optional<Yeargroup> find(long id) throws SQLException {
        Yeargroup yeargroup = null;
        List<Group> groups = new ArrayList<>();

        findGroupsPS.setLong(1, id);
        ResultSet findGroupsRS = findGroupsPS.executeQuery();
        try (GroupDAO groupDAO = new GroupDAO()) {
            while (findGroupsRS.next()) {
                Optional<Group> optionalGroup = groupDAO.find(findGroupsRS.getLong("CLASS_GROUP"));
                if (optionalGroup.isPresent())
                    groups.add(optionalGroup.get());
                else
                    throw new NotFoundException();
            }
        }
        findPS.setLong(1, id);
        ResultSet findRS = findPS.executeQuery();
        if (findRS.next()) yeargroup = fromResultSet(findRS, groups);
        return Optional.ofNullable(yeargroup);
    }

    @Override
    public List<Yeargroup> findAll() throws SQLException {
        List<Yeargroup> yeargroups = new ArrayList<>();
        ResultSet findAllRS = findAllPS.executeQuery();
        try (GroupDAO groupDAO = new GroupDAO()) {
            while (findAllRS.next()) {
                List<Group> groups = new ArrayList<>();
                findGroupsPS.setLong(1, findAllRS.getLong("ID"));
                ResultSet findGroupsRS = findGroupsPS.executeQuery();

                while (findGroupsRS.next()) {
                    Optional<Group> optionalGroup = groupDAO.find(findGroupsRS.getLong("CLASS_GROUP"));
                    if (optionalGroup.isPresent())
                        groups.add(optionalGroup.get());
                    else
                        throw new NotFoundException();
                }
                yeargroups.add(fromResultSet(findAllRS, groups));
            }
        }
        return yeargroups;
    }

    @Override
    public Yeargroup persist(Yeargroup yeargroup) throws SQLException {
        populate(persistPS, yeargroup);
        Yeargroup yg = super.persist(); // problem: save in static list but object not whole (same for the other classes)
        Yeargroup.popYeargroupInList(yg); // solution: keep object for id but remove it from static list (same for the other classes)
        for (Group g: yeargroup.getGroups())
            yg.addGroup(g);
        persistGroups(yg);
        return find(yg.getId()).get();
    }

    @Override
    public void update(Yeargroup yeargroup) throws SQLException {
        populate(updatePS, yeargroup);
        updatePS.setLong(2, yeargroup.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Yeargroup yeargroup) throws SQLException {
        popPS.setString(1, yeargroup.getLabel());
    }

    private void persistGroups(Yeargroup yeargroup) throws SQLException {
        for (Group g: yeargroup.getGroups())
            persistGroup(yeargroup, g);
    }

    private void persistGroup(Yeargroup yeargroup, Group group) throws SQLException {
        populateGroup(persistGroupPS, yeargroup, group);
        persistGroupPS.executeUpdate();
    }

    public void updateGroups(Yeargroup yeargroup) throws SQLException {
        findGroupsPS.setLong(1, yeargroup.getId());
        ResultSet findGroupsRS = findGroupsPS.executeQuery();
        while (findGroupsRS.next()) { // Remove former groups
            removeGroupPS.setLong(1, findGroupsRS.getLong("ID"));
            removeGroupPS.executeUpdate();
        }
        persistGroups(yeargroup); // Add new groups
    }

    private void populateGroup(PreparedStatement popYeargroupPS, Yeargroup yeargroup, Group group) throws SQLException {
        popYeargroupPS.setLong(1, yeargroup.getId());
        popYeargroupPS.setLong(2, group.getId());
    }

    @Override
    public String getTableName() {
        return "YEARGROUP";
    }
}
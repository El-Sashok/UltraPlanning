package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.*;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class LessonDAO extends AbstractDAO<Lesson> {
    private final PreparedStatement findGroupsPS;
    private final PreparedStatement persistGroupPS ;
    private final PreparedStatement updateGroupPS;
    private final PreparedStatement findModulesPS;
    private final PreparedStatement persistModulePS;
    private final PreparedStatement updateModulePS;
    ReservationDAO reservationDAO;
    GroupDAO groupDAO;
    ModuleDAO moduleDAO;

    public LessonDAO() throws SQLException {
        super("INSERT INTO LESSON(ID, TYPE) VALUES (?,?)",
                "UPDATE LESSON SET ID=?, TYPE=? WHERE ID=?");
        this.reservationDAO = new ReservationDAO();
        this.groupDAO = new GroupDAO();
        this.moduleDAO = new ModuleDAO();

        findGroupsPS = connection.prepareStatement("SELECT * FROM LESSON_GROUPS WHERE LESSON=?");
        persistGroupPS = connection.prepareStatement("INSERT INTO LESSON_GROUPS(LESSON, CLASS_GROUP) VALUES (?,?)");
        updateGroupPS = connection.prepareStatement("UPDATE LESSON_GROUPS SET LESSON=?, CLASS_GROUP=? WHERE ID=?");

        findModulesPS = connection.prepareStatement("SELECT * FROM LESSON_MODULES WHERE LESSON=?");
        persistModulePS = connection.prepareStatement("INSERT INTO LESSON_MODULES(LESSON, MODULE) VALUES (?,?)");
        updateModulePS = connection.prepareStatement("UPDATE LESSON_MODULES SET LESSON=?, MODULE=? WHERE ID=?");
    }

    @Override
    protected Lesson fromResultSet(ResultSet resultSet) { return null; }

    protected Lesson fromResultSet(ResultSet resultSet, ArrayList<Group> groups, ArrayList<Module> modules) throws SQLException {
        Reservation reservation = reservationDAO.find(resultSet.getLong("ID")).get();

        Lesson lesson = new Lesson(reservation, Lesson.Type.valueOf(resultSet.getString("TYPE")));
        for (Group g: groups) {
            lesson.addGroup(g);
        }
        for (Module m: modules) {
            lesson.addModule(m);
        }
        return lesson;
    }

    @Override
    public Optional<Lesson> find(long id) throws SQLException {
        Lesson lesson = null;
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Module> modules = new ArrayList<>();
        findPS.setLong(1, id);
        findGroupsPS.setLong(1, id);
        findModulesPS.setLong(1, id);
        ResultSet rs_findGroupsPS = findGroupsPS.executeQuery();
        while (rs_findGroupsPS.next()) groups.add(groupDAO.fromResultSet(rs_findGroupsPS));

        ResultSet rs_findModulesPS = findModulesPS.executeQuery();
        while (rs_findModulesPS.next()) modules.add(moduleDAO.fromResultSet(rs_findModulesPS));

        ResultSet rs_findPS = findPS.executeQuery();
        while (rs_findPS.next()) lesson = fromResultSet(rs_findPS, groups, modules);

        return Optional.ofNullable(lesson);
    }

    @Override
    public Lesson persist(Lesson lesson) throws SQLException {
        Reservation reservation = new Reservation(lesson.getId(),
                lesson.getStartDate(),
                lesson.getEndDate(),
                lesson.getLabel(),
                lesson.getMemo(),
                lesson.getState(),
                lesson.getRoom());
        Reservation r = reservationDAO.persist(reservation, "LESSON");
        lesson.setId(r.getId());
        populate(persistPS, lesson);
        Lesson pers = super.persist();
        persistGroups(lesson);
        persistModules(lesson);
        return pers;
    }

    @Override
    public void update(Lesson lesson) throws SQLException {
        populate(updatePS, lesson);
        updatePS.setLong(3, lesson.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Lesson lesson) throws SQLException {
        popPS.setLong(1, lesson.getId());
        popPS.setString(2, lesson.getType().toString());
    }

    private void persistGroups(Lesson lesson) throws SQLException {
        for (Group g: lesson.getGroups()) {
            populateGroup(persistGroupPS, lesson, g);
            persistGroupPS.executeUpdate();
        }
    }

    private void updateGroup(Lesson lesson, Group group, Long lessonGroupID) throws SQLException {
        populateGroup(updateGroupPS, lesson, group);
        updateGroupPS.setLong(3, lessonGroupID);
        updateGroupPS.executeUpdate();
    }

    private void populateGroup(PreparedStatement popGroupPS, Lesson lesson, Group group) throws SQLException {
        popGroupPS.setLong(1, lesson.getId());
        popGroupPS.setLong(2, group.getId());
    }

    private void persistModules(Lesson lesson) throws SQLException {
        for (Module m: lesson.getModules()) {
            populateModule(persistModulePS, lesson, m);
            persistModulePS.executeUpdate();
        }
    }

    private void updateModule(Lesson lesson) throws SQLException {
    }

    private void populateModule(PreparedStatement popGroupPS, Lesson lesson, Module module) throws SQLException {
        popGroupPS.setLong(1, lesson.getId());
        popGroupPS.setLong(2, module.getId());
    }

    @Override
    public String getTableName() {
        return "LESSON";
    }
}
package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
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
    private final PreparedStatement removeGroupPS;
    private final PreparedStatement findModulesPS;
    private final PreparedStatement persistModulePS;
    private final PreparedStatement removeModulePS;

    public LessonDAO() throws SQLException {
        super("INSERT INTO LESSON(ID, TYPE) VALUES (?,?)",
                "UPDATE LESSON SET ID=?, TYPE=? WHERE ID=?");
        findGroupsPS = connection.prepareStatement("SELECT * FROM LESSON_GROUPS WHERE LESSON=?");
        persistGroupPS = connection.prepareStatement("INSERT INTO LESSON_GROUPS(LESSON, CLASS_GROUP) VALUES (?,?)");
        removeGroupPS = connection.prepareStatement("DELETE FROM LESSON_GROUPS WHERE ID=?");

        findModulesPS = connection.prepareStatement("SELECT * FROM LESSON_MODULES WHERE LESSON=?");
        persistModulePS = connection.prepareStatement("INSERT INTO LESSON_MODULES(LESSON, MODULE) VALUES (?,?)");
        removeModulePS = connection.prepareStatement("DELETE FROM LESSON_MODULES WHERE ID=?");
    }

    @Override
    protected Lesson fromResultSet(ResultSet resultSet) { return null; }

    protected Lesson fromResultSet(ResultSet resultSet, List<Group> groups, List<Module> modules) throws SQLException {
        for (Reservation r: Reservation.getReservationList()) {
            if (r.getId() == resultSet.getLong("ID") && r instanceof Lesson)
                return (Lesson) r;
        }

        Reservation reservation;
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservation = reservationDAO.find(resultSet.getLong("ID")).get();
            Reservation.popReservationList(reservation);
        }

        Lesson lesson = new Lesson(reservation, Lesson.Type.valueOf(resultSet.getString("TYPE")));
        for (Group g: groups)
            lesson.addGroup(g);
        for (Module m: modules)
            lesson.addModule(m);

        return lesson;
    }

    @Override
    public Optional<Lesson> find(long id) throws SQLException {
        Lesson lesson = null;
        List<Group> groups = new ArrayList<>();
        List<Module> modules = new ArrayList<>();
        findPS.setLong(1, id);
        findGroupsPS.setLong(1, id);
        findModulesPS.setLong(1, id);
        ResultSet rs_findGroupsPS = findGroupsPS.executeQuery();
        try (GroupDAO groupDAO = new GroupDAO()) {
            while (rs_findGroupsPS.next()) groups.add(groupDAO.find(rs_findGroupsPS.getLong("CLASS_GROUP")).get());
        }
        ResultSet rs_findModulesPS = findModulesPS.executeQuery();
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            while (rs_findModulesPS.next()) modules.add(moduleDAO.find(rs_findModulesPS.getLong("MODULE")).get());
        }
        ResultSet rs_findPS = findPS.executeQuery();
        while (rs_findPS.next()) lesson = fromResultSet(rs_findPS, groups, modules);

        return Optional.ofNullable(lesson);
    }

    @Override
    public Lesson persist(Lesson lesson) throws SQLException {
        Reservation r;
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            r = reservationDAO.persist(lesson, "LESSON");
        }
        Reservation.popReservationList(r);

        lesson.setId(r.getId());
        populate(persistPS, lesson);
        Lesson pers = super.persist();
        persistGroups(lesson);
        persistModules(lesson);
        Reservation.popReservationList(pers);
        return find(pers.getId()).get();
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

    public void updateGroups(Lesson lesson) throws SQLException {
        findGroupsPS.setLong(1, lesson.getId());
        ResultSet findGroupsRS = findGroupsPS.executeQuery();
        while (findGroupsRS.next()) {
            removeGroupPS.setLong(1, findGroupsRS.getLong("ID"));
            removeGroupPS.executeUpdate();
        }
        persistGroups(lesson);
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

    public void updateModules(Lesson lesson) throws SQLException {
        findModulesPS.setLong(1, lesson.getId());
        ResultSet findModulesRS = findModulesPS.executeQuery();
        while (findModulesRS.next()) {
            removeModulePS.setLong(1, findModulesRS.getLong("ID"));
            removeModulePS.executeUpdate();
        }
        persistModules(lesson);
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

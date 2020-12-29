package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Student;
import fr.univtln.mapare.exceptions.NotFoundException;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class GroupDAO extends AbstractDAO<Group>{
    private final PreparedStatement findMembersPS;
    private final PreparedStatement persistMemberPS;
    private final PreparedStatement updateMemberPS;

    public GroupDAO() throws SQLException {
        super("INSERT INTO CLASS_GROUP(LABEL) VALUES (?)",
                "UPDATE CLASS_GROUP SET LABEL=? WHERE ID=?");

        this.findMembersPS = connection.prepareStatement("SELECT * FROM GROUP_MEMBERS WHERE CLASS_GROUP=?");
        this.persistMemberPS = connection.prepareStatement("INSERT INTO GROUP_MEMBERS(CLASS_GROUP, STUDENT) VALUES (?,?)");
        this.updateMemberPS = connection.prepareStatement("UPDATE GROUP_MEMBERS SET CLASS_GROUP=?, STUDENT=? WHERE ID=?");
    }

    @Override
    protected Group fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Group fromResultSet(ResultSet resultSet, List<Student> students) throws SQLException {
        for (Group g: Group.getGroupList()) {
            if (g.getId() == resultSet.getLong("ID"))
                return g;
        }

        Group group = new Group(resultSet.getInt("ID"),
                resultSet.getString("LABEL"));
        for (Student s : students) {
            group.addStudent(s);
        }
        return group;
    }

    @Override
    public Optional<Group> find(long id) throws SQLException {
        Group group  = null;
        List<Student> students = new ArrayList<>();

        findMembersPS.setLong(1, id);
        ResultSet findMembersRS = findMembersPS.executeQuery();
        StudentDAO studentDAO = new StudentDAO();
        while (findMembersRS.next()) {
            Optional<Student> optionalStudent = studentDAO.find(findMembersRS.getInt("STUDENT"));
            if (optionalStudent.isPresent())
                students.add(optionalStudent.get());
            else
                throw new NotFoundException();
        }
        studentDAO.close();
        findPS.setLong(1, id);
        ResultSet findRS = findPS.executeQuery();
        if (findRS.next()) group = fromResultSet(findRS, students);
        return Optional.ofNullable(group);
    }

    @Override
    public List<Group> findAll() throws SQLException {
        List<Group> groups = new ArrayList<>();
        ResultSet findAllRS = findAllPS.executeQuery();
        StudentDAO studentDAO = new StudentDAO();
        while (findAllRS.next()) {
            List<Student> students = new ArrayList<>();
            findMembersPS.setLong(1, findAllRS.getLong("ID"));
            ResultSet findMembersRS = findMembersPS.executeQuery();

            while (findMembersRS.next()) {
                Optional<Student> optionalStudent = studentDAO.find(findMembersRS.getInt("STUDENT"));
                if (optionalStudent.isPresent())
                    students.add(optionalStudent.get());
                else
                    throw new NotFoundException();
            }
            groups.add(fromResultSet(findAllRS, students));
        }
        studentDAO.close();
        return groups;
    }

    @Override
    public Group persist(Group group) throws SQLException {
        populate(persistPS, group);
        Group gp = super.persist();
        Group.popGroupInList(gp);
        for (Student s: group.getStudents())
            gp.addStudent(s);
        persistMembers(gp);
        return find(gp.getId()).get();
    }

    @Override
    public void update(Group group) throws SQLException {
        populate(updatePS, group);
        updatePS.setLong(2, group.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Group group) throws SQLException {
        popPS.setString(1, group.getLabel());
    }

    public void persistMembers(Group group) throws SQLException {
        for (Student s: group.getStudents())
            persistMember(group, s);
    }

    public void persistMember(Group group, Student member) throws SQLException {
        populateMember(persistMemberPS, group, member);
        persistMemberPS.executeUpdate();
    }

    private void updateMember(Group group, Student student) throws SQLException {
        populateMember(updateMemberPS, group, student);
        updateMemberPS.setLong(3, group.getId());
        updateMemberPS.executeUpdate();
    }

    private void populateMember(PreparedStatement popGroupPS, Group group, Student student) throws SQLException {
        popGroupPS.setLong(1, group.getId());
        popGroupPS.setLong(2, student.getId());
    }

    @Override
    public void remove(long id) throws SQLException {
        connection.createStatement().execute("DELETE FROM GROUP_MEMBERS WHERE CLASS_GROUP=" + id);
        super.remove(id);
    }

    @Override
    public void clean() throws SQLException {
        connection.createStatement().execute("DELETE FROM GROUP_MEMBERS");
        super.clean();
    }

    @Override
    public String getTableName() {
        return "CLASS_GROUP";
    }
}

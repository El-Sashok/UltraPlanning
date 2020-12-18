package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Student;
import fr.univtln.mapare.exceptions.DataAccessException;
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
    private final PreparedStatement findStudentPS;
    private final StudentDAO studentDAO;

    public GroupDAO() throws SQLException {
        super("", "");
        this.studentDAO= new StudentDAO();
        this.findMembersPS = connection.prepareStatement("SELECT * FROM GROUP_MEMBERS WHERE CLASS_GROUP=?");
        this.findStudentPS = connection.prepareStatement("SELECT * FROM STUDENT WHERE ID=?");
    }

    @Override
    protected Group fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Group fromResultSet(ResultSet resultSet, List<Student> students) throws SQLException {
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
        findPS.setLong(1, id);
        findMembersPS.setLong(1, id);
        ResultSet rs_findPS = findPS.executeQuery();
        ResultSet rs_findGP = findMembersPS.executeQuery();

        while (rs_findGP.next()) {
            findStudentPS.setLong(1, rs_findGP.getInt("STUDENT"));
            ResultSet rs_findST = findStudentPS.executeQuery();
            rs_findST.next();
            students.add(studentDAO.fromResultSet(rs_findST));
        }
        while (rs_findPS.next()) group = fromResultSet(rs_findPS, students);
        return Optional.ofNullable(group);
    }

    @Override
    public List<Group> findAll() throws SQLException {
        List<Group> entityList = new ArrayList<>();
        ResultSet rs_findPS = findAllPS.executeQuery();

        while (rs_findPS.next()) {
            List<Student> students = new ArrayList<>();
            findMembersPS.setLong(1, rs_findPS.getLong("ID"));
            ResultSet rs_findGP = findMembersPS.executeQuery();

            while (rs_findGP.next()) {
                findStudentPS.setLong(1, rs_findGP.getLong("STUDENT"));
                ResultSet rs_findST = findStudentPS.executeQuery();
                rs_findST.next();
                students.add(studentDAO.fromResultSet(rs_findST));
            }
            entityList.add(fromResultSet(rs_findPS, students));
        }
        return entityList;
    }

    @Override
    public Group persist(Group group) {
        return null;
    }

    @Override
    public void update(Group group) {

    }

    @Override
    public String getTableName() {
        return "CLASS_GROUP";
    }
}

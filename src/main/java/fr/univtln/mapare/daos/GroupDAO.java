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
    private final PreparedStatement findGP;
    private final PreparedStatement findST;
    private final StudentDAO studentDAO;

    public GroupDAO() throws DataAccessException {
        super("", "");
        this.studentDAO= new StudentDAO();
        PreparedStatement _findGP = null;
        try {
                _findGP = connection.prepareStatement("SELECT * FROM GROUP_MEMBERS WHERE CLASS_GROUP=?");
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        this.findGP = _findGP;
        PreparedStatement _findST = null;
        try {
            _findST = connection.prepareStatement("SELECT * FROM STUDENT WHERE ID=?");
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        this.findST = _findST;
    }

    @Override
    protected Group fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Group fromResultSet(ResultSet resultSet, ArrayList<Student> students) throws SQLException {
        Group group = new Group(resultSet.getInt("ID"),
                resultSet.getString("LABEL"));
        for (Student s : students) {
            group.addStudent(s);
        }
        return group;
    }

    @Override
    public Optional<Group> find(long id) throws DataAccessException {
        Group group  = null;
        ArrayList<Student> students = new ArrayList<>();
        try {
            findPS.setLong(1, id);
            findGP.setLong(1, id);
            ResultSet rs_findPS = findPS.executeQuery();
            ResultSet rs_findGP = findGP.executeQuery();
            while (rs_findGP.next()) {
                findST.setLong(1, rs_findGP.getInt("STUDENT"));
                ResultSet rs_findST = findST.executeQuery();
                rs_findST.next();
                students.add(studentDAO.fromResultSet(rs_findST));
            }
            while (rs_findPS.next()) group = fromResultSet(rs_findPS, students);
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return Optional.ofNullable(group);
    }

    @Override
    public List<Group> findAll() throws DataAccessException {
        List<Group> entityList = new ArrayList<>();
        try {
            ResultSet rs_findPS = findAllPS.executeQuery();
            while (rs_findPS.next()){
                ArrayList<Student> students = new ArrayList<>();
                try {
                    findGP.setLong(1, rs_findPS.getLong("ID"));
                    ResultSet rs_findGP = findGP.executeQuery();
                    while (rs_findGP.next()) {
                        findST.setLong(1, rs_findGP.getLong("STUDENT"));
                        ResultSet rs_findST = findST.executeQuery();
                        rs_findST.next();
                        students.add(studentDAO.fromResultSet(rs_findST));
                    }
                    entityList.add(fromResultSet(rs_findPS, students));
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
    public Group persist(Group group) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Group group) throws DataAccessException {

    }

    @Override
    public String getTableName() {
        return "CLASS_GROUP";
    }

    public ArrayList<Student> getStudents(long id) throws DataAccessException {
        ArrayList<Student> students = new ArrayList<>();
        try {
            findGP.setLong(1, id);
            ResultSet rs_findGP = findGP.executeQuery();
            while (rs_findGP.next()) {
                findST.setLong(1, rs_findGP.getInt("STUDENT"));
                ResultSet rs_findST = findST.executeQuery();
                rs_findST.next();
                students.add(studentDAO.fromResultSet(rs_findST));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return students;
    }
}

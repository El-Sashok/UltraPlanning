package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.AdmissionExam;
import fr.univtln.mapare.entities.AdmissionExamLabel;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdmissionExamDAO extends AbstractDAO<AdmissionExam> {
    private final PreparedStatement findStudentsPS;
    private final PreparedStatement persistStudentPS;
    private final PreparedStatement updateStudentPS;

    public AdmissionExamDAO() throws SQLException {
        super("INSERT INTO ADMISSIONEXAM(ID, LABEL) VALUES (?,?)",
                "UPDATE ADMISSIONEXAM SET ID=?, LABEL=? WHERE ID=?");
        findStudentsPS = connection.prepareStatement("SELECT * FROM ADMISSIONEXAM_STUDENTS WHERE ADMISSIONEXAM=?");
        persistStudentPS = connection.prepareStatement("INSERT INTO ADMISSIONEXAM_STUDENTS(ADMISSIONEXAM, STUDENT) VALUES (?,?)");
        updateStudentPS = connection.prepareStatement("UPDATE ADMISSIONEXAM_STUDENTS SET ADMISSIONEXAM=?, STUDENT=? WHERE ID=?");



    }

    @Override
    protected AdmissionExam fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    protected AdmissionExam fromResultSet(ResultSet resultSet, AdmissionExamLabel admissionExamLabel, List<Student> students) throws SQLException {
        for (Reservation r: Reservation.getReservationList()) {
            if (r.getId() == resultSet.getLong("ID") && r instanceof AdmissionExam)
                return (AdmissionExam) r;
        }

        Reservation reservation;
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservation = reservationDAO.find(resultSet.getLong("ID")).get();
            Reservation.popReservationList(reservation);
        }

        AdmissionExam admissionExam = new AdmissionExam(reservation, admissionExamLabel);
        for (Student s: students)
            admissionExam.addStudent(s);

        return admissionExam;
    }

    @Override
    public Optional<AdmissionExam> find(long id) throws SQLException {
        AdmissionExam admissionExam = null;
        List<Student> students = new ArrayList<>();
        findPS.setLong(1, id);
        findStudentsPS.setLong(1, id);
        ResultSet rs_findStudentsPS = findStudentsPS.executeQuery();
        try (StudentDAO studentDAO = new StudentDAO()) {
            while (rs_findStudentsPS.next()) students.add(studentDAO.find(rs_findStudentsPS.getLong("STUDENT")).get());
        }
        ResultSet rs_findPS = findPS.executeQuery();
        try (AdmissionExamLabelDAO admissionExamLabelDAO = new AdmissionExamLabelDAO()) {
            while (rs_findPS.next())
                admissionExam = fromResultSet(rs_findPS, admissionExamLabelDAO.find(rs_findPS.getLong("LABEL")).get(), students);
        }

        return Optional.ofNullable(admissionExam);
    }

    @Override
    public AdmissionExam persist(AdmissionExam admissionExam) throws SQLException {
        Reservation r;
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            r = reservationDAO.persist(admissionExam, "ADMISSION_EXAM");
        }
        Reservation.popReservationList(r);

        admissionExam.setId(r.getId());
        populate(persistPS, admissionExam);
        AdmissionExam pers = super.persist();
        persistStudents(admissionExam);
        Reservation.popReservationList(pers);
        return find(pers.getId()).get();
    }

    @Override
    public void update(AdmissionExam admissionExam) throws SQLException {
        populate(updatePS, admissionExam);
        updatePS.setLong(3, admissionExam.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, AdmissionExam admissionExam) throws SQLException {
        popPS.setLong(1, admissionExam.getId());
        popPS.setLong(2, admissionExam.getAdmissionExamLabel().getId());
    }

    private void persistStudents(AdmissionExam admissionExam) throws SQLException {
        for (Student s: admissionExam.getStudents()) {
            populateStudent(persistStudentPS, admissionExam, s);
            persistStudentPS.executeUpdate();
        }
    }

    private void updateStudent(AdmissionExam admissionExam, Student student, long admissionExamStudentID) throws SQLException {
        populateStudent(updateStudentPS, admissionExam, student);
        updateStudentPS.setLong(3, admissionExamStudentID);
        updateStudentPS.executeUpdate();
    }

    private void populateStudent(PreparedStatement popStudentPS, AdmissionExam admissionExam, Student student) throws SQLException {
        popStudentPS.setLong(1, admissionExam.getId());
        popStudentPS.setLong(2, student.getId());
    }

    @Override
    public String getTableName() {
        return "ADMISSIONEXAM";
    }


}

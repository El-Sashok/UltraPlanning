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

/**
 * Classe DAO d'un concours
 * @author Equipe MAPARE
 * @version 1.0
 */
public class AdmissionExamDAO extends AbstractDAO<AdmissionExam> {
    private final PreparedStatement findStudentsPS;
    private final PreparedStatement persistStudentPS;

    /**
     * Constructeur d'une DAO de concours
     * @throws SQLException
     */
    public AdmissionExamDAO() throws SQLException {
        super("INSERT INTO ADMISSIONEXAM(ID, LABEL) VALUES (?,?)",
                "UPDATE ADMISSIONEXAM SET ID=?, LABEL=? WHERE ID=?");
        findStudentsPS = connection.prepareStatement("SELECT * FROM ADMISSIONEXAM_STUDENTS WHERE ADMISSIONEXAM=?");
        persistStudentPS = connection.prepareStatement("INSERT INTO ADMISSIONEXAM_STUDENTS(ADMISSIONEXAM, STUDENT) VALUES (?,?)");
    }

    /**
     * /!\ Mettode non implémenté /!\
     * @param resultSet .
     * @return null
     * @throws SQLException Exception SQL
     */
    @Override
    protected AdmissionExam fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    /**
     * Permet de créer une entité concours à partir d'un resultset
     * @param resultSet ResultSet qui contient les information pour créer l'entité
     * @param admissionExamLabel Intitulé du concours
     * @param students Liste d'étudiants qui participent au concours
     * @return une entité concours
     * @throws SQLException Exception SQL
     */
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

    /**
     * Permet de peupler les Prepared Statements pour AdmissionExam
     * @param popPS La Prepared Statements à peupler
     * @param admissionExam L'entité concours qui détiens les informations
     * @throws SQLException Exception SQL
     */
    private void populate(PreparedStatement popPS, AdmissionExam admissionExam) throws SQLException {
        popPS.setLong(1, admissionExam.getId());
        popPS.setLong(2, admissionExam.getAdmissionExamLabel().getId());
    }

    /**
     * Permet de sauvegarder dans la base de donnée les étudiants inscrit concours
     * @param admissionExam L'entité concours
     * @throws SQLException Exception SQL
     */
    private void persistStudents(AdmissionExam admissionExam) throws SQLException {
        for (Student s: admissionExam.getStudents()) {
            populateStudent(persistStudentPS, admissionExam, s);
            persistStudentPS.executeUpdate();
        }
    }

    /**
     * Permet de peupler les Prepared Statements pour AdmissionExam_Students
     * @param popStudentPS La Prepared Statements à peupler
     * @param admissionExam L'entité concours qui détiens les informations
     * @param student l'étudiant impliqué
     * @throws SQLException Exception SQL
     */
    private void populateStudent(PreparedStatement popStudentPS, AdmissionExam admissionExam, Student student) throws SQLException {
        popStudentPS.setLong(1, admissionExam.getId());
        popStudentPS.setLong(2, student.getId());
    }

    @Override
    public String getTableName() {
        return "ADMISSIONEXAM";
    }


}

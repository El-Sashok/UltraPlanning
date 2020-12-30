package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.AdmissionExam;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdmissionExamDAO extends AbstractDAO<AdmissionExam> {
    private final PreparedStatement findStudentsByReservation;

    public AdmissionExamDAO() throws SQLException {
        super("INSERT INTO ADMISSION_EXAM(RESERVATION, STUDENT) VALUES (?,?)",
                "UPDATE ADMISSION_EXAM SET RESERVATION=?, STUDENT=? WHERE ID=?");
        this.findStudentsByReservation = connection.prepareStatement("SELECT RESERVATION, STUDENT FROM ADMISSION_EXAM WHERE RESERVATION=?");

    }

    @Override
    protected AdmissionExam fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    protected AdmissionExam fromResultSet(ResultSet resultSet, List<Student> students) throws SQLException {
        Reservation reservation = null;
        for (Reservation r: Reservation.getReservationList()) {
            if (r.getId() == resultSet.getLong("RESERVATION"))
                reservation = r;
        }
        if (reservation == null) {
            ReservationDAO reservationDAO = new ReservationDAO();
            reservation = reservationDAO.find(resultSet.getLong("RESERVATION")).get();
            reservationDAO.close();
        }
        Reservation.popReservationList(reservation);

        AdmissionExam admissionExam = new AdmissionExam(reservation);
        for (Student s: students)
            admissionExam.addStudent(s);

        return admissionExam;
    }

    @Override
    //Find with the id of the reservation
    public Optional<AdmissionExam> find(long reservationID) throws SQLException {
        AdmissionExam admissionExam;
        List<Student> students = new ArrayList<>();

        findStudentsByReservation.setLong(1, reservationID);
        ResultSet findStudentsRS = findStudentsByReservation.executeQuery();
        StudentDAO studentDAO = new StudentDAO();
        if (findStudentsRS.next()) students.add(studentDAO.find(findStudentsRS.getLong("STUDENT")).get());
        studentDAO.close();

        admissionExam = fromResultSet(findStudentsRS, students); // Need to test ! (check if findStudentsRS still have RESERVATION)

        return Optional.ofNullable(admissionExam);
    }

    @Override
    public AdmissionExam persist(AdmissionExam admissionExam) throws SQLException {
        return null;
    }

    @Override
    public void update(AdmissionExam admissionExam) throws SQLException {

    }

    @Override
    public String getTableName() {
        return "ADMISSION_EXAM";
    }


}

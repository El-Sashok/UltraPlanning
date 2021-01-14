package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.NotFoundException;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class ReservationDAO extends AbstractDAO<Reservation> {
    private final PreparedStatement findManagersPS;
    private final PreparedStatement persistManagerPS;
    private final PreparedStatement removeManagerPS;

    public ReservationDAO() throws SQLException {
        super("INSERT INTO RESERVATION(START, END, LABEL, MEMO, TYPE, ROOM, STATE) VALUES (?,?,?,?,?,?,?)",
                "UPDATE RESERVATION SET START=?, END=?, LABEL=?, MEMO=?, TYPE=?, ROOM=?, STATE=? WHERE ID=?");

        findManagersPS = connection.prepareStatement("SELECT * FROM MANAGERS WHERE RESERVATION=?");
        persistManagerPS = connection.prepareStatement("INSERT INTO MANAGERS(RESERVATION, MANAGER) VALUES (?,?)");
        removeManagerPS = connection.prepareStatement("DELETE FROM MANAGERS WHERE ID=?");
    }

    @Override
    protected Reservation fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    protected Reservation fromResultSet(ResultSet resultSet, List<Teacher> managers) throws SQLException {
        for (Reservation r: Reservation.getReservationList()) {
            if (r.getId() == resultSet.getLong("ID"))
                return r;
        }
        Room room;
        try (RoomDAO roomDAO = new RoomDAO()) {
            room = roomDAO.find(resultSet.getLong("ROOM")).get();
        }

        Reservation reservation = new Reservation(resultSet.getLong("ID"),
                resultSet.getTimestamp("START").toLocalDateTime(),
                resultSet.getTimestamp("END").toLocalDateTime(),
                resultSet.getString("LABEL"),
                resultSet.getString("MEMO"),
                Reservation.State.valueOf(resultSet.getString("STATE")),
                room);
        for (Teacher t: managers) reservation.addTeacher(t);

        return reservation;
    }

    @Override
    public Optional<Reservation> find(long id) throws SQLException {
        Reservation reservation = null;
        List<Teacher> managers = new ArrayList<>();
        findManagersPS.setLong(1, id);
        ResultSet findManagersRS = findManagersPS.executeQuery();
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            while (findManagersRS.next()) {
                Optional<Teacher> optionalManager = teacherDAO.find(findManagersRS.getLong("MANAGER"));
                if (optionalManager.isPresent())
                    managers.add(optionalManager.get());
                else
                    throw new NotFoundException();
            }
        }
        findPS.setLong(1, id);
        ResultSet findRS = findPS.executeQuery();
        if (findRS.next()) reservation = fromResultSet(findRS, managers);

        return Optional.ofNullable(reservation);
    }

    @Override
    public List<Reservation> findAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        ResultSet findAllRS = findAllPS.executeQuery();

        while (findAllRS.next()) {
            if (findAllRS.getString("TYPE") == null) { //Mandatory to test null outside the switch ! otherwise return NullErrorException
                Reservation reservation = find(findAllRS.getLong("ID")).get();
                reservations.add(reservation);
            }
            else {
                switch (findAllRS.getString("TYPE")) {
                    case "LESSON":
                        try (LessonDAO lessonDAO = new LessonDAO()) {
                            Lesson lesson = lessonDAO.find(findAllRS.getLong("ID")).get();
                            reservations.add(lesson);
                        }
                        break;
                    case "ADMISSION_EXAM":
                        try (AdmissionExamDAO admissionExamDAO = new AdmissionExamDAO()) {
                            AdmissionExam admissionExam = admissionExamDAO.find(findAllRS.getLong("ID")).get();
                            reservations.add(admissionExam);
                        }
                        break;
                    case "DEFENCE":
                        try (DefenceDAO defenceDAO = new DefenceDAO()) {
                            Defence defence = defenceDAO.find(findAllRS.getLong("ID")).get();
                            reservations.add(defence);
                        }
                        break;
                    case "EXAM_BOARD":
                        try (ExamBoardDAO examBoardDAO = new ExamBoardDAO()) {
                            ExamBoard examBoard = examBoardDAO.find(findAllRS.getLong("ID")).get();
                            reservations.add(examBoard);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return reservations;
    }

    @Override
    public Reservation persist(Reservation reservation) throws SQLException {
        populate(persistPS, reservation);
        Reservation r = super.persist();
        Reservation.popReservationList(r);
        reservation.setId(r.getId());
        persistManagers(reservation);
        return find(r.getId()).get();
    }

    public Reservation persist(Reservation reservation, String type) throws SQLException {
        populate(persistPS, reservation, type);
        Reservation r = super.persist();
        Reservation.popReservationList(r);
        reservation.setId(r.getId());
        persistManagers(reservation);
        return find(r.getId()).get();
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        populate(updatePS, reservation);
        updatePS.setLong(8, reservation.getId());
        super.update();
    }

    public void update(Reservation reservation, String type) throws SQLException {
        populate(updatePS, reservation, type);
        updatePS.setLong(8, reservation.getId());
        super.update();
        updateManagers(reservation);
    }

    private void populate(PreparedStatement popPS, Reservation reservation) throws SQLException {
        populate(popPS, reservation, null);
    }

    private void populate(PreparedStatement popPS, Reservation reservation, String type) throws SQLException {
        popPS.setTimestamp(1, Timestamp.valueOf(reservation.getStartDate()));
        popPS.setTimestamp(2, Timestamp.valueOf(reservation.getEndDate()));
        popPS.setString(3, reservation.getLabel());
        popPS.setString(4, reservation.getMemo());
        popPS.setString(5, type);
        popPS.setLong(6,reservation.getRoom().getId());
        popPS.setString(7, reservation.getState().toString());
    }

    private void persistManagers(Reservation reservation) throws SQLException {
        for (Teacher t: reservation.getManagers())
            persistManager(reservation, t);
    }

    private void persistManager(Reservation reservation, Teacher manager) throws SQLException {
        populateManager(persistManagerPS, reservation, manager);
        persistManagerPS.executeUpdate();
    }

    public void updateManagers(Reservation reservation) throws SQLException {
        findManagersPS.setLong(1, reservation.getId());
        ResultSet findManagersRS = findManagersPS.executeQuery();
        while (findManagersRS.next()) {
            removeManagerPS.setLong(1, findManagersRS.getLong("ID"));
            removeManagerPS.executeUpdate();
        }
        persistManagers(reservation);
    }

    private void populateManager(PreparedStatement popManagerPS, Reservation reservation, Teacher manager) throws SQLException {
        popManagerPS.setLong(1, reservation.getId());
        popManagerPS.setLong(2, manager.getId());
    }

    @Override
    public String getTableName() {
        return "RESERVATION";
    }
}

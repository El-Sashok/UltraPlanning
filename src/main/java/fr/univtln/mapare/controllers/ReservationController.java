package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ReservationDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.UpdateExceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.UpdateExceptions.NotChangedException;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.RoomTimeBreakException;

import java.io.StringReader;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class ReservationController {

    public static void loadReservations() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.findAll();
        reservationDAO.close();
    }

    public static List<Room> findFreeRooms(LocalDateTime startDate, LocalDateTime endDate) {
        List<Room> rooms = new ArrayList<>(Room.getRoomList());
        for (Reservation r : Reservation.getReservationList())
            if (r.getState() == Reservation.State.NP)
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate))
                    rooms.removeIf(room -> room.getId() == r.getRoom().getId());
        return rooms;
    }

    public static List<Reservation> findPersonalReservations() {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : Reservation.getReservationList()){
            if (Session.getStatus() == Session.Status.STUDENT) {
                if (r instanceof Lesson){
                    group:
                    for (Group g : ((Lesson) r).getGroups()){
                        for (Student s : g.getStudents()){
                            if (s.getEmail().equals(Session.getLogin())) {
                                reservations.add(r);
                                break group;
                            }
                        }
                    }
                }
                else if (r instanceof Defence) {
                    if (((Defence) r).getStudent().getEmail().equals(Session.getLogin())){
                        reservations.add(r);
                    }
                }
                else if (r instanceof AdmissionExam){
                    for (Student s : ((AdmissionExam) r).getStudents()){
                        if (s.getEmail().equals(Session.getLogin())){
                            reservations.add(r);
                            break;
                        }
                    }
                }
            }
            else if (Session.getStatus() == Session.Status.TEACHER) {
                for (Teacher t : r.getManagers()){
                    if (t.getEmail().equals(Session.getLogin())){
                        reservations.add(r);
                        break;
                    }
                }
            }
        }
        return reservations;
    }

    public static void createReservation(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, List<Teacher> managers) throws SQLException {
        for (Reservation r : Reservation.getReservationList()){
            if (r.getState() == Reservation.State.NP) {
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                    for (Teacher dbTeacher : r.getManagers())
                        for (Teacher LocalTeacher : managers)
                            if (dbTeacher.getId() == LocalTeacher.getId())
                                throw new ManagerTimeBreakException(LocalTeacher);
                    if (r.getRoom().getId() == room.getId())
                        throw new RoomTimeBreakException(room);
                }
            }
        }

        Reservation reservation = new Reservation(-1, startDate, endDate, label, memo, state, room);
        for (Teacher t : managers) reservation.addTeacher(t);
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.persist(reservation);
        reservationDAO.close();
    }

    public static void changeStatusReservation(Reservation reservation, Reservation.State state) throws SQLException, NotChangedException {
        if (reservation.getState() == state) //If state is not modified, throw an exception
            throw new NotChangedException(reservation);

        reservation.setState(state);
        update(reservation);
    }

    public static void addManagerReservation(Reservation reservation, Teacher manager) throws SQLException, NotChangedException {
        for (Teacher m : reservation.getManagers())
            if (m == manager) //If manager is already there, throw an exception
                throw new NotChangedException(reservation);

        //TODO check horaire prof

        reservation.addTeacher(manager);
        update(reservation);
    }

    public static void removeManagerReservation(Reservation reservation, Teacher manager) throws SQLException, EmptyAttributeException {
        if (reservation.getManagers().size() == 1)
            if (reservation.getManagers().get(0) == manager) //if manager is the sole in list, throw an exception
                throw new EmptyAttributeException("removeManagerReservation", reservation);

        reservation.removeTeacher(manager);
        update(reservation);
    }

    public static void changeMemoReservation(Reservation reservation, String memo) throws SQLException, NotChangedException{
        if (reservation.getMemo().equals(memo))
            throw new NotChangedException(reservation);

        reservation.setMemo(memo);
        update(reservation);
    }

    private static void update(Reservation reservation) throws SQLException{
        try (ReservationDAO reservationDAO = new ReservationDAO()) { //Need to check the class to put the good type in DB
            if (Lesson.class.equals(reservation.getClass())) {
                reservationDAO.update(reservation, "LESSON");
            } else if (AdmissionExam.class.equals(reservation.getClass())) {
                reservationDAO.update(reservation, "ADMISSION_EXAM");
            } else if (Defence.class.equals(reservation.getClass())) {
                reservationDAO.update(reservation, "DEFENCE");
            } else if (ExamBoard.class.equals(reservation.getClass())) {
                reservationDAO.update(reservation, "EXAM_BOARD");
            } else {
                reservationDAO.update(reservation);
            }
        }
    }
}

package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ReservationDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class ReservationController {

    private ReservationController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les réservations
     * @throws SQLException Exception SQL
     */
    public static void loadReservations() throws SQLException {
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservationDAO.findAll();
        }
    }

    /**
     * Permet de rechercher toutes les salles libres pendent un horaire donné
     * @param startDate Début de l'horaire souhaité
     * @param endDate Fin de l'horaire souhaité
     * @return Une liste de salles
     */
    public static List<Room> findFreeRooms(LocalDateTime startDate, LocalDateTime endDate) {
        List<Room> rooms = new ArrayList<>(Room.getRoomList());
        for (Reservation r : Reservation.getReservationList())
            if (r.getState() == Reservation.State.NP)
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate))
                    rooms.removeIf(room -> room.getId() == r.getRoom().getId());
        return rooms;
    }

    /**
     * Permet de récupérer l'emploie du temps de la personne connectée
     * @return Une Liste de réservations
     */
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

    /**
     * Permet de créer une réservation simple
     * @param startDate Début de la réservation
     * @param endDate Fin de la réservation
     * @param label Intitulé de la réservation
     * @param memo Informations complémentaires
     * @param state État de la réservation
     * @param room Salle dans laquelle se déroule la réservation
     * @param managers Liste des Enseignant en charge de la salle
     * @throws SQLException Exception SQL
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
     * @throws RoomTimeBreakException La salle est déjà occupé pendant cette horaire
     */
    public static void createReservation(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, List<Teacher> managers) throws SQLException, ManagerTimeBreakException, RoomTimeBreakException {
        for (Reservation r : Reservation.getReservationList()){ // récupère la liste de toutes les reservations
            if (r.getState() == Reservation.State.NP) { // vérifie si la reservation n'as pas été déplacé ou annulé
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) { // vérifie les collision de réservation
                    for (Teacher dbTeacher : r.getManagers())
                        for (Teacher LocalTeacher : managers)
                            if (dbTeacher.getId() == LocalTeacher.getId()) // vérifie si un enseignant est déjà occupé pendant cette horaire
                                throw new ManagerTimeBreakException(LocalTeacher);
                    if (r.getRoom().getId() == room.getId()) // vérifie si la salle n'est pas déjà occupée
                        throw new RoomTimeBreakException(room);
                }
            }
        }

        Reservation reservation = new Reservation(-1, startDate, endDate, label, memo, state, room);
        for (Teacher t : managers) reservation.addTeacher(t);
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservationDAO.persist(reservation);
        }
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
    public static void createReservation(Reservation res, List<Teacher> managers) throws RoomTimeBreakException,
            SQLException, ManagerTimeBreakException {
        createReservation(res.getStartDate(), res.getEndDate(), res.getLabel(), res.getMemo(), res.getState(),
                res.getRoom(), managers);
    }
}

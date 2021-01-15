package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ReservationDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.BadPracticesException;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.timebreakexceptions.GroupTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;
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
     * Fonction d'initialisation : Elle permet de charger toutes les réservations
     * @throws SQLException Exception SQL
     */
    public static void loadReservations() throws SQLException {
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservationDAO.findAll();
        }
    }

    /**
     * Permet de rechercher toutes les salles libres pendant un horaire donné
     * @param startDate Début de l'horaire souhaité
     * @param endDate Fin de l'horaire souhaité
     * @return Une liste de salles
     */
    public static List<Room> findFreeRooms(LocalDateTime startDate, LocalDateTime endDate) {
        List<Room> rooms = new ArrayList<>(Room.getRoomList());
        for (Reservation r : Reservation.getReservationList())
            if (r.getState() == Reservation.State.NP)
                if (ControllerTools.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate))
                    rooms.removeIf(room -> room.getId() == r.getRoom().getId());
        return rooms;
    }

    /**
     * Permet de récupérer l'emploi du temps de la personne connectée
     * @return Une Liste de réservations
     */
    public static List<Reservation> findPersonalReservations() {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : Reservation.getReservationList()){
            if (Session.getInstance().getStatus() == Session.Status.STUDENT) {
                if (r instanceof Lesson){
                    group:
                    for (Group g : ((Lesson) r).getGroups()){
                        for (Student s : g.getStudents()){
                            if (s.getEmail().equals(Session.getInstance().getLogin())) {
                                reservations.add(r);
                                break group;
                            }
                        }
                    }
                }
                else if (r instanceof Defence) {
                    if (((Defence) r).getStudent().getEmail().equals(Session.getInstance().getLogin())){
                        reservations.add(r);
                    }
                }
                else if (r instanceof AdmissionExam){
                    for (Student s : ((AdmissionExam) r).getStudents()){
                        if (s.getEmail().equals(Session.getInstance().getLogin())){
                            reservations.add(r);
                            break;
                        }
                    }
                }
            }
            else if (Session.getInstance().getStatus() == Session.Status.TEACHER) {
                for (Teacher t : r.getManagers()){
                    if (t.getEmail().equals(Session.getInstance().getLogin())){
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
     * @param managers Liste des enseignants en charge de la réservation
     * @throws SQLException Exception SQL
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cet horaire
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cet horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    public static void createReservation(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, List<Teacher> managers) throws SQLException, ManagerTimeBreakException, RoomTimeBreakException, IncorrectEndHourException {
        checkCollision(startDate, endDate, room, managers);

        Reservation reservation = new Reservation(-1, startDate, endDate, label, memo, state, room);
        reservation.setManagers(managers);
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservationDAO.persist(reservation);
        }
    }

    /**
     * Permet de vérifier qu'il n'y ait pas de collision avec une autre réservation
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param room Salle dans laquelle se déroule le cours
     * @param managers Professeur en charge de la réservation
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
     * @throws RoomTimeBreakException La salle est déjà occupé pendant cette horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    static void checkCollision(LocalDateTime startDate, LocalDateTime endDate, Room room, List<Teacher> managers) throws ManagerTimeBreakException, RoomTimeBreakException, IncorrectEndHourException {
        ControllerTools.checkStartAfterEnd(startDate, endDate);

        checkCollisionConstraints(startDate, endDate, managers);

        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && ControllerTools.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                if (room.equals(r.getRoom()))
                    throw new RoomTimeBreakException(room);
                checkCollisionTeachers(r, managers);
            }
        }
    }

    /**
     * Permet de vérifier qu'il n'y ait pas de collision avec les contraintes d'emploi du temps des enseignants
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param managers Professeurs en charge de la réservation
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
    */
    private static void checkCollisionConstraints(LocalDateTime startDate, LocalDateTime endDate, List<Teacher> managers) throws ManagerTimeBreakException {
        for (Teacher t : managers)
            for (Constraint c : t.getConstraints())
                ConstraintController.checkConflicts(startDate, endDate, c);
    }

    /**
     * Permet de vérifier qu'il n'y ait pas de collision avec avec une autre réservation concernant les enseignants
     * @param r La réservation
     * @param managers Professeurs en charge de la réservation
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
    */
    private static void checkCollisionTeachers(Reservation r, List<Teacher> managers) throws ManagerTimeBreakException {
        for (Teacher t : managers)
            if (r.getManagers().contains(t))
                throw new ManagerTimeBreakException(t);
    }

    /**
     * Permet de changer l'état d'une réservation
     * @param reservation La réservation
     * @param state État de la réservation
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeStatusReservation(Reservation reservation, Reservation.State state) throws SQLException, NotChangedException {
        if (reservation.getState() == state) //If state is not modified, throw an exception
            throw new NotChangedException(reservation);

        reservation.setState(state);
        update(reservation);
    }

    /**
     * Permet de changer les enseignants d'une réservation
     * @param reservation La réservation
     * @param managers Nouvelle liste d'enseignants encadrant la réservation
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException managers ne contient pas de manager
     * @throws NotChangedException Aucune modification apportée
     * @throws ManagerTimeBreakException Un des enseignants dans managers n'est pas disponible
     */
    public static void changeManagers(Reservation reservation, List<Teacher> managers) throws SQLException, EmptyAttributeException, NotChangedException, ManagerTimeBreakException {
        if (managers.size() == 0) //if managers is empty
            throw new EmptyAttributeException("changeManagersReservation", reservation);
        if (managers.size() == reservation.getManagers().size())
            if (reservation.getManagers().containsAll(managers)) //if it's the same list
                throw new NotChangedException(reservation);
        for (Reservation r : Reservation.getReservationList())
            if (!r.equals(reservation) && r.isNP() && ControllerTools.checkTimeBreak(r, reservation))
                checkCollisionTeachers(r, managers);
        checkCollisionConstraints(reservation.getStartDate(), reservation.getEndDate(), managers);

        reservation.setManagers(managers);
        try (ReservationDAO reservationDAO = new ReservationDAO()) {
            reservationDAO.updateManagers(reservation);
        }
    }

    /**
     * Permet de changer le mémo d'une réservation
     * @param reservation La réservation
     * @param memo Informations complémentaires
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeMemo(Reservation reservation, String memo) throws SQLException, NotChangedException {
        if (reservation.getMemo().equals(memo))
            throw new NotChangedException(reservation);

        reservation.setMemo(memo);
        update(reservation);
    }

    /**
     * Permet de changer la salle d'une réservation
     * @param reservation La réservation
     * @param room Salle dans laquelle se déroule la réservation
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cette horaire
     */
    public static void changeRoom(Reservation reservation, Room room) throws SQLException, NotChangedException, RoomTimeBreakException {
        if (reservation.getRoom().equals(room))
            throw new NotChangedException(reservation);
        for (Reservation r : Reservation.getReservationList())
            if (r.getRoom().equals(room))
                throw new RoomTimeBreakException(room);

        reservation.setRoom(room);
        update(reservation);
    }

    /**
     * Permert de déplacer à un nouveau créneau la réservation
     * @param reservation La réservation à déplacer
     * @param startDate La nouvelle date de début
     * @param endDate La nouvelle date de fin
     * @param room La nouvelle salle
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cet horaire
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cet horaire
     * @throws BadPracticesException La nouvelle reservation ne respecte pas une règle de bienséance
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cet horaire
     * @throws GroupTimeBreakException Un groupe est déjà occupé pendant cet horaire
     */
    public static void moveReservation(Reservation reservation, LocalDateTime startDate, LocalDateTime endDate, Room room) throws SQLException, NotChangedException, IncorrectEndHourException, RoomTimeBreakException, ManagerTimeBreakException, BadPracticesException, StudentTimeBreakException, GroupTimeBreakException {
        if (Reservation.class.equals(reservation.getClass())) {
            createReservation(startDate, endDate, reservation.getLabel(), reservation.getMemo(), reservation.getState(), room, reservation.getManagers());
        } else if (Lesson.class.equals(reservation.getClass())) {
            LessonController.createLesson(startDate, endDate, reservation.getLabel(), reservation.getMemo(), reservation.getState(), room, ((Lesson) reservation).getType(), ((Lesson) reservation).getModules(), ((Lesson) reservation).getGroups() ,reservation.getManagers());
        } else if (AdmissionExam.class.equals(reservation.getClass())) {
            AdmissionExamController.createAdmissionExam(startDate, endDate, reservation.getLabel(), reservation.getMemo(), reservation.getState(), room, ((AdmissionExam) reservation).getAdmissionExamLabel(), reservation.getManagers() ,((AdmissionExam) reservation).getStudents());
        } else if (Defence.class.equals(reservation.getClass())) {
            DefenceController.createDefence(startDate, endDate, reservation.getLabel(), reservation.getMemo(), reservation.getState(), room, ((Defence) reservation).getStudent(), reservation.getManagers());
        } else if (ExamBoard.class.equals(reservation.getClass())) {
            ExamBoardController.createExamBoard(startDate, endDate, reservation.getLabel(), reservation.getMemo(), reservation.getState(), room, ((ExamBoard) reservation).getYeargroup(), reservation.getManagers());
        }
        changeStatusReservation(reservation, Reservation.State.POSTPONED);
    }

    /**
     * Permet de pouvoir update en BDD en fonction du type de résa
     * @param reservation La réservationà mettre à jour
     * @throws SQLException Exception SQL
     */
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

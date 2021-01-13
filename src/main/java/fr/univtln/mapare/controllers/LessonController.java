package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.LessonDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class LessonController {

    private LessonController() {}

    /**
     * Permet de créer une reservation de cours si il n'y a aucune collisions avec une autre reservation puis la sauvegarde dans la base de donnée
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param label Intitulé du cours
     * @param memo Information complémentaire
     * @param state État de la reservation
     * @param room Salle dans laquelle se déroule le cours
     * @param type Type de cours
     * @param modules Module enseigné
     * @param groups Groupe qui participe au cours
     * @param managers Professeur en charge de la classe
     * @throws SQLException Exception SQL
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
     * @throws RoomTimeBreakException La salle est déjà occupé pendant cette horaire
     * @throws GroupTimeBreakException Le groupe est déjà occupé pendant cette horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cette horaire
     */
    public static void createLesson(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                    Reservation.State state, Room room, Lesson.Type type, List<Module> modules,
                                    List<Group> groups, List<Teacher> managers) throws SQLException, ManagerTimeBreakException, RoomTimeBreakException, GroupTimeBreakException, StudentTimeBreakException {

        for (Reservation r : Reservation.getReservationList()){ // récupère la liste de toutes les reservations
            if (r.getState() == Reservation.State.NP) { // vérifie si la reservation n'as pas été déplacé ou annulé
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)){ // vérifie les collision de réservation
                    for (Teacher dbTeacher : r.getManagers()){
                        for (Teacher LocalTeacher : managers){
                            if (dbTeacher.getId() == LocalTeacher.getId()) { // vérifie si un enseignant est déjà occupé pendant cette horaire
                                throw new ManagerTimeBreakException(LocalTeacher);
                            }
                        }
                    }
                    if (r.getRoom().getId() == room.getId()) { // vérifie si la salle n'est pas déjà occupée
                        throw new RoomTimeBreakException(room);
                    }
                    else if (r instanceof Lesson){
                        for (Group dbGroup : ((Lesson) r).getGroups()){
                            for (Group LocalGroup : groups){
                                if (dbGroup.getId() == LocalGroup.getId()) { // vérifie si un groupe est déjà occupé pendant cette horaire
                                    throw new GroupTimeBreakException(LocalGroup);
                                }
                            }
                        }
                    }
                    else if (r instanceof Defence){
                        for (Group g : groups){
                            for (Student s : g.getStudents()){
                                if (s.getId() == ((Defence) r).getStudent().getId()) { // vérifie si un étudiant est déjà occupé pendant cette horaire
                                    throw new StudentTimeBreakException(s);
                                }
                            }
                        }
                    }
                }
            }
        }

        Lesson lesson = new Lesson(-1, startDate, endDate, label, memo, state, room, type);
        for (Module m : modules) lesson.addModule(m);
        for (Group g : groups) lesson.addGroup(g);
        for (Teacher t : managers) lesson.addTeacher(t);
        try (LessonDAO lessonDAO = new LessonDAO()) {
            lessonDAO.persist(lesson);
        }
    }
}

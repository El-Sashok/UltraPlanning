package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.LessonDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.BadPracticesException;
import fr.univtln.mapare.exceptions.timebreakexceptions.*;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
                                    List<Group> groups, List<Teacher> managers) throws SQLException,
            ManagerTimeBreakException, RoomTimeBreakException, GroupTimeBreakException, StudentTimeBreakException,
            BadPracticesException {
        for (Teacher t: managers)
            for (Constraint c : t.getConstraints())
                ConstraintController.checkConflicts(startDate, endDate, c);

        checkGoodPractices(startDate, endDate, groups, managers, modules);

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
                    else if (r instanceof AdmissionExam){
                        for (Group g : groups){
                            for (Student s : g.getStudents()){
                                if (((AdmissionExam) r).getStudents().contains(s)) {
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

    /**
     * Permet de vérifier qu'un cours que l'on s'apprête à créer respecte les règles de bienséance :
     * - Pas plus de 9h de cours par jours
     * - Pas toujours le même module durant plus d'une semaine
     * @param start Date (avec heure) à laquelle commence le cours
     * @param end Date (avec heure) à laquelle se termine le cours
     * @param groups Liste des groupes qui participent au cours
     * @param managers Liste des enseignants en charge de la salle
     * @param modules Liste des modules associé au cours
     * @return Vrais (True) si il n'y à pas de problèmes de bienséance
     * @throws BadPracticesException Exception si une règle de bienséance n'est pas respectée.
     */
    public static boolean checkGoodPractices(LocalDateTime start, LocalDateTime end, List<Group> groups,
                                             List<Teacher> managers, List<Module> modules) throws BadPracticesException {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());


        List<Lesson> lessonsOfDay = Lesson.getLessonsForDay(calendar.get(Calendar.DAY_OF_YEAR));

        // Teste si les groupes ont le même module pendent une semaine

//        List<Lesson> lessonsOfWeek = Lesson.getLessonsForWeek(calendar.get(Calendar.WEEK_OF_YEAR));
//        for (Group g : groups){
//            for (Lesson l : lessonsOfWeek){
//                if (l.getGroups().contains(g)){
//
//                }
//            }
//        }

        long[] nbHoursG = new long[groups.size()];
        long[] nbHoursM = new long[managers.size()];

        for (int i = 0; i < nbHoursG.length; i++){
            nbHoursG[i] = Duration.between(start, end).toHours();
            if (nbHoursG[i] > 9) {
                throw new BadPracticesException(groups.get(i));
            }
        }

        for (int i = 0; i < nbHoursM.length; i++){
            nbHoursG[i] = Duration.between(start, end).toHours();
            if (nbHoursG[i] > 9) {
                throw new BadPracticesException(groups.get(i));
            }
        }

        for (Lesson lesson : lessonsOfDay){
            for (int i = 0; i < groups.size(); i++) {
                if (lesson.getGroups().contains(groups.get(i))) {
                    nbHoursG[i] += Duration.between(lesson.getStartDate(), lesson.getEndDate()).toHours();
                    if (nbHoursG[i] > 9) {
                        throw new BadPracticesException(groups.get(i));
                    }
                }
            }
            for (int i = 0; i < managers.size(); i++) {
                if (lesson.getManagers().contains(managers.get(i))) {
                    nbHoursM[i] += Duration.between(lesson.getStartDate(), lesson.getEndDate()).toHours();
                    if (nbHoursM[i] > 9) {
                        throw new BadPracticesException(managers.get(i));
                    }
                }
            }
        }
        return true;
    }
}

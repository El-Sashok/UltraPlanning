package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamDAO;
import fr.univtln.mapare.daos.LessonDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.BadPracticesException;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.timebreakexceptions.*;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public abstract class LessonController {

    private LessonController() {}

    /**
     * Permet de créer une reservation de cours si il n'y a aucune collision avec une autre reservation puis la sauvegarde dans la base de données
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param label Intitulé du cours
     * @param memo Informations complémentaires
     * @param state État de la reservation
     * @param room Salle dans laquelle se déroule le cours
     * @param type Type de cours
     * @param modules Modules enseignés
     * @param groups Groupe qui participe au cours
     * @param managers Professeur en charge de la classe
     * @throws SQLException Exception SQL
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cet horaire
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cet horaire
     * @throws GroupTimeBreakException Le groupe est déjà occupé pendant cet horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cet horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    public static void createLesson(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                    Reservation.State state, Room room, Lesson.Type type, List<Module> modules,
                                    List<Group> groups, List<Teacher> managers) throws SQLException,
            ManagerTimeBreakException, RoomTimeBreakException, GroupTimeBreakException, StudentTimeBreakException,
            BadPracticesException, IncorrectEndHourException {
        checkCollision(startDate, endDate, room, modules, groups, managers);

        Lesson lesson = new Lesson(-1, startDate, endDate, label, memo, state, room, type);
        for (Module m : modules) lesson.addModule(m);
        for (Group g : groups) lesson.addGroup(g);
        for (Teacher t : managers) lesson.addTeacher(t);
        try (LessonDAO lessonDAO = new LessonDAO()) {
            lessonDAO.persist(lesson);
        }
    }

    /**
     * Permet de vérifier qu'il n'y à pas de collision avec une autre réservation
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param room Salle dans laquelle se déroule le cours
     * @param modules Modules enseignés
     * @param groups Groupe qui participe au cours
     * @param managers Professeur en charge de la classe
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
     * @throws BadPracticesException La nouvelle reservation ne respecte pas une règle de bienséance
     * @throws RoomTimeBreakException La salle est déjà occupé pendant cette horaire
     * @throws GroupTimeBreakException Le groupe est déjà occupé pendant cette horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cette horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    private static void checkCollision(LocalDateTime startDate, LocalDateTime endDate, Room room, List<Module> modules, List<Group> groups, List<Teacher> managers) throws ManagerTimeBreakException, BadPracticesException, RoomTimeBreakException, GroupTimeBreakException, StudentTimeBreakException, IncorrectEndHourException {
        ControllerTools.checkStartAfterEnd(startDate, endDate);

        for (Teacher t: managers)
            for (Constraint c : t.getConstraints())
                ConstraintController.checkConflicts(startDate, endDate, c);

        checkGoodPractices(startDate, endDate, groups, managers, modules);

        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                if (room.equals(r.getRoom()))
                    throw new RoomTimeBreakException(room);
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
                if (r instanceof Lesson) {
                    for (Group dbGroup : ((Lesson) r).getGroups())
                        for (Group LocalGroup : groups)
                            if (dbGroup.getId() == LocalGroup.getId())
                                throw new GroupTimeBreakException(LocalGroup);
                } else if (r instanceof Defence) {
                    for (Group g : groups)
                        for (Student s : g.getStudents())
                            if (s.getId() == ((Defence) r).getStudent().getId())
                                throw new StudentTimeBreakException(s);
                } else if (r instanceof AdmissionExam) {
                    for (Group g : groups)
                        for (Student s : g.getStudents())
                            if (((AdmissionExam) r).getStudents().contains(s))
                                throw new StudentTimeBreakException(s);
                }
            }
        }
    }

    /**
     * Permet de changer les groupes participant au cours
     * @param lesson Le cours
     * @param groups Nouvelle liste de groupes pour le cours
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException groups ne contient pas de groupe
     * @throws NotChangedException Aucune modification apportée
     * @throws StudentTimeBreakException Un des étudiants d'un groupe dans groups n'est pas disponible
     */
    public static void changeGroups(Lesson lesson, List<Group> groups) throws SQLException, EmptyAttributeException, NotChangedException, StudentTimeBreakException, GroupTimeBreakException {
        if (groups.size() == 0)
            throw new EmptyAttributeException("changeGroups", lesson);
        if (groups.size() == lesson.getGroups().size())
            if (lesson.getGroups().containsAll(groups))
                throw new NotChangedException(lesson);

        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), lesson.getStartDate(), lesson.getEndDate())) {
                if (r instanceof Lesson) {
                    for (Group dbGroup : ((Lesson) r).getGroups())
                        for (Group LocalGroup : groups)
                            if (dbGroup.getId() == LocalGroup.getId())
                                throw new GroupTimeBreakException(LocalGroup);
                } else if (r instanceof Defence) {
                    for (Group g : groups)
                        for (Student s : g.getStudents())
                            if (s.getId() == ((Defence) r).getStudent().getId())
                                throw new StudentTimeBreakException(s);
                } else if (r instanceof AdmissionExam) {
                    for (Group g : groups)
                        for (Student s : g.getStudents())
                            if (((AdmissionExam) r).getStudents().contains(s))
                                throw new StudentTimeBreakException(s);
                }
            }
        }

        lesson.setGroups(groups);
        try (LessonDAO lessonDAO = new LessonDAO()) {
            lessonDAO.updateGroups(lesson);
        }
    }

    /**
     * Permet de vérifier qu'un cours que l'on s'apprête à créer respecte les règles de bienséance :
     * - Pas plus de 9h de cours par jour
     * - Pas toujours le même module durant plus d'une semaine
     * - Pas terminer tous les jours à 18h ou plus
     * @param start Date (avec heure) à laquelle commence le cours
     * @param end Date (avec heure) à laquelle se termine le cours
     * @param groups Liste des groupes qui participent au cours
     * @param managers Liste des enseignants en charge de la salle
     * @param modules Liste des modules associés au cours
     * @throws BadPracticesException Exception si une règle de bienséance n'est pas respectée.
     */
    public static void checkGoodPractices(LocalDateTime start, LocalDateTime end, List<Group> groups,
                                          List<Teacher> managers, List<Module> modules) throws BadPracticesException {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);

        calendar.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());


        List<Lesson> lessonsOfDay = Lesson.getLessonsForDay(calendar.get(Calendar.DAY_OF_YEAR));

        // Teste si les groupes ont le même module pendent une semaine
        Map<Integer, List<Lesson>> lessonsOfWeek = Lesson.getLessonsForWeek(calendar.get(Calendar.WEEK_OF_YEAR) - 1);
        boolean[][] breakFlagG = new boolean[groups.size()][modules.size()];
        int[] checkAlwaysAfterSix = new int[groups.size()];
        for (int g = 0; g < groups.size(); g++){
            for (int m = 0; m < modules.size(); m++){
                for (int i = 0 ; i < 6 ; i++){
                    for (Lesson l : lessonsOfWeek.get(i)){
                        if (l.getGroups().contains(groups.get(g)) && !l.getModules().contains(modules.get(m))) {
                            breakFlagG[g][m] = true;
                        }
                        if (m == 0 && l.getEndDate().isAfter(LocalDateTime.of(l.getEndDate().toLocalDate(), LocalTime.of(17,59)))){
                            checkAlwaysAfterSix[g]++;
                        }
                    }
                }
            }
        }


        for (int g = 0; g < groups.size(); g++){
            if (checkAlwaysAfterSix[g] > 4){
                throw new BadPracticesException("AFTER_SIX", groups.get(g));
            }
            for (int m = 0; m < modules.size(); m++){
                if (!breakFlagG[g][m]){
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                        throw new BadPracticesException(groups.get(g), modules.get(m));
                }
            }
        }


        long[] nbHoursG = new long[groups.size()];
        long[] nbHoursM = new long[managers.size()];

        for (int i = 0; i < nbHoursG.length; i++){
            nbHoursG[i] = Duration.between(start, end).toHours();
            if (nbHoursG[i] > 9) {
                throw new BadPracticesException("TO_MANY_HOURS", groups.get(i));
            }
        }

        for (int i = 0; i < nbHoursM.length; i++){
            nbHoursG[i] = Duration.between(start, end).toHours();
            if (nbHoursG[i] > 9) {
                throw new BadPracticesException("TO_MANY_HOURS", groups.get(i));
            }
        }

        for (Lesson lesson : lessonsOfDay){
            for (int i = 0; i < groups.size(); i++) {
                if (lesson.getGroups().contains(groups.get(i))) {
                    nbHoursG[i] += Duration.between(lesson.getStartDate(), lesson.getEndDate()).toHours();
                    if (nbHoursG[i] > 9) {
                        throw new BadPracticesException("TO_MANY_HOURS", groups.get(i));
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
    }
}

package fr.univtln.mapare.controllers;

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
     * Permet de récupérer l'emploi du temps d'un groupe
     * @param group Le groupe recherché
     * @return Une Liste de réservations
     */
    public static List<Lesson> findByGroup(Group group) {
        List<Lesson> lessons = new ArrayList<>();

        for (Reservation r : Reservation.getReservationList())
            if (r instanceof Lesson)
                if (((Lesson) r).getGroups().contains(group))
                    lessons.add((Lesson) r);

        return lessons;
    }

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
     * Permet de vérifier qu'il n'y ait pas de collision avec une autre réservation
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param room Salle dans laquelle se déroule le cours
     * @param modules Modules enseignés
     * @param groups Liste de groupe qui participent au cours
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
            if (r.isNP() && ControllerTools.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                if (room.equals(r.getRoom()))
                    throw new RoomTimeBreakException(room);
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
                checkCollisionGroups(r, groups);
            }
        }
    }

    /**
     * Permet de vérifier qu'il n'y ait pas de collision avec une autre réservation concernant les groupes
     * @param r La réservation
     * @param groups Liste de groupe qui participent au cours
     * @throws GroupTimeBreakException Le groupe est déjà occupé pendant cette horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cette horaire
     */
    private static void checkCollisionGroups(Reservation r, List<Group> groups) throws GroupTimeBreakException, StudentTimeBreakException {
        if (r instanceof Lesson) {
            for (Group dbGroup : ((Lesson) r).getGroups())
                for (Group LocalGroup : groups)
                    if (dbGroup.getId() == LocalGroup.getId())
                        throw new GroupTimeBreakException(LocalGroup);
        } else if (r instanceof Defence) {
            for (Group g : groups)
                if (g.getStudents().contains(((Defence) r).getStudent()))
                    throw new StudentTimeBreakException(((Defence) r).getStudent());
        } else if (r instanceof AdmissionExam) {
            for (Group g : groups)
                for (Student s : g.getStudents())
                    if (((AdmissionExam) r).getStudents().contains(s))
                        throw new StudentTimeBreakException(s);
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
     * @throws GroupTimeBreakException Un des groupes dans groups n'est pas disponible
     * @throws BadPracticesException La nouvelle reservation ne respecte pas une règle de bienséance
     */
    public static void changeGroups(Lesson lesson, List<Group> groups) throws SQLException, EmptyAttributeException, NotChangedException, StudentTimeBreakException, GroupTimeBreakException, BadPracticesException {
        if (groups.size() == 0)
            throw new EmptyAttributeException("changeGroups", lesson);
        if (groups.size() == lesson.getGroups().size())
            if (lesson.getGroups().containsAll(groups))
                throw new NotChangedException(lesson);

        checkGoodPractices(lesson.getStartDate(), lesson.getEndDate(), groups, lesson.getManagers(), lesson.getModules());

        for (Reservation r : Reservation.getReservationList()) {
            if (!r.equals(lesson) && r.isNP() && ControllerTools.checkTimeBreak(r, lesson)) {
                checkCollisionGroups(r, groups);
            }
        }

        lesson.setGroups(groups);
        try (LessonDAO lessonDAO = new LessonDAO()) {
            lessonDAO.updateGroups(lesson);
        }
    }

    /**
     * Permet de changer les modules associés au cours
     * @param lesson Le cours
     * @param modules Nouvelle liste de modules pour le cours
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException modules ne contient pas de module
     * @throws NotChangedException Aucune modification apportée
     * @throws BadPracticesException La nouvelle reservation ne respecte pas une règle de bienséance
     */
    public static void changeModules(Lesson lesson, List<Module> modules) throws SQLException, EmptyAttributeException, NotChangedException, BadPracticesException {
        if (modules.size() == 0)
            throw new EmptyAttributeException("changeModules", lesson);
        if (modules.size() == lesson.getModules().size())
            if (lesson.getModules().containsAll(modules))
                throw new NotChangedException(lesson);

        checkGoodPractices(lesson.getStartDate(), lesson.getEndDate(), lesson.getGroups(), lesson.getManagers(), modules);

        lesson.setCourses(modules);
        try (LessonDAO lessonDAO = new LessonDAO()) {
            lessonDAO.updateModules(lesson);
        }
    }

    /**
     * Permet de changer le type du cours
     * @param lesson Le cours
     * @param type Le type du cours
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeType(Lesson lesson, Lesson.Type type) throws SQLException, NotChangedException {
        if (lesson.getType().equals(type))
            throw new NotChangedException(lesson);

        lesson.setType(type);
        try (LessonDAO lessonDAO = new LessonDAO()) {
            lessonDAO.update(lesson);
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
     * @param managers Liste des enseignants en charge de la réservation
     * @param modules Liste des modules associés au cours
     * @throws BadPracticesException Exception si une règle de bienséance n'est pas respectée.
     */
    public static void checkGoodPractices(LocalDateTime start, LocalDateTime end, List<Group> groups,
                                          List<Teacher> managers, List<Module> modules) throws BadPracticesException {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);

        calendar.set(start.getYear(), start.getMonthValue() - 1, start.getDayOfMonth());

        List<Lesson> lessonsOfDay = Lesson.getLessonsForDay(calendar.get(Calendar.DAY_OF_YEAR));

        // TODO: faire en sorte que ça marche
        // Teste si les groupes ont le même module pendent une semaine (ne marche pas bien)
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
                if (breakFlagG[g][m]){
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

    public static List<Lesson> findPersonalLessonsByModule(Module module) {
        return findPersonalLessonsByModule(ReservationController.findPersonalReservations(), module);
    }

    public static List<Lesson> findPersonalLessonsByModule(List<Reservation> privatereservations, Module module) {
        List<Lesson> returnList = new ArrayList<>();
        for (Reservation r : privatereservations)
            if (r instanceof Lesson && ((Lesson)r).getModules().contains(module))
                returnList.add((Lesson) r);
        return returnList;
    }
}

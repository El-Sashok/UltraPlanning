package fr.univtln.mapare.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe entité d'une contrainte d' emploi du temps
 * @author Equipe MAPARE
 * @version 1.0
 */
public class Constraint implements Entity {
    private long id;
    private LocalDate dayOfTheWeek;
    private LocalTime startHour;
    private LocalTime endHour;
    private Teacher teacher; //needed for dao
    private static final List<Constraint> CONSTRAINTS = new ArrayList<>();

    public static final String[] JOURSDELASEMAINE = {"Lundi", "Mardi", "Mecredi", "Jeudi", "Vendredi", "Samedi"};

    //Constructors

    /**
     * Constructeur d'une contrainte d' emploi du temps
     * @param id Identifiant d'une contrainte d' emploi du temps
     * @param dayOfTheWeek Jour de la semaine concerné par la contrainte (on prend une date mais seul le jour compte)
     * @param startHour Début d'une contrainte d' emploi du temps
     * @param endHour Fin d'une contrainte d' emploi du temps
     */
    public Constraint(long id, LocalDate dayOfTheWeek, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        if (id != -1) // To differentiate the ones which are yet in database
            CONSTRAINTS.add(this);
    }

    //Needed for TeacherDao
    /**
     * Constructeur d'une contrainte d' emploi du temps avec un Teacher
     * @param id Identifiant d'une contrainte d' emploi du temps
     * @param dayOfTheWeek Jour de la semaine concerné par la contrainte (on prend une date mais seul le jour compte)
     * @param startHour Début d'une contrainte d' emploi du temps
     * @param endHour Fin d'une contrainte d' emploi du temps
     * @param teacher Identifiant d'un Enseignant
     */
    public Constraint(long id, LocalDate dayOfTheWeek, LocalTime startHour, LocalTime endHour, Teacher teacher) {
        this.id = id;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        this.teacher = teacher;
        if (id != -1) // To differentiate the ones which are yet in database
            CONSTRAINTS.add(this);
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static List<Constraint> getConstraintList() {
        return CONSTRAINTS;
    }

    public static void popConstraintInList(Constraint constraint) {
        CONSTRAINTS.remove(constraint);
    }

    public LocalDate getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(LocalDate dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    //Methods
    @Override
    public String toString() {
        return JOURSDELASEMAINE[dayOfTheWeek.getDayOfWeek().getValue() - 1] + " de " + startHour + " à " + endHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Constraint)) return false;
        Constraint that = (Constraint) o;
        return dayOfTheWeek.equals(that.dayOfTheWeek) &&
                startHour.equals(that.startHour) &&
                endHour.equals(that.endHour) &&
                Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfTheWeek, startHour, endHour, teacher);
    }

    /**
     * @deprecated
     * @return Returns a string
     */
    @Deprecated
    public String print() {
        return "Constraint{" +
                "startDate=" + startHour +
                ", endDate=" + endHour +
                '}';
    }
}

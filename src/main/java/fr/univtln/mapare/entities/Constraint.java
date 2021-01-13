package fr.univtln.mapare.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe entité d'une contrainte d' emploie du temps
 * @author Equipe MAPARE
 * @version 1.0
 */
public class Constraint implements Entity {
    private long id;
    private LocalDate day;
    private LocalTime start;
    private LocalTime end;
    private Teacher teacher; //needed for dao
    private static final List<Constraint> CONSTRAINTS = new ArrayList<>();

    //Constructors

    /**
     * Constructeur d'une contrainte d' emploie du temps
     * @param id Identifiant d'une contrainte d' emploie du temps
     * @param day
     * @param start Début d'une contrainte d' emploie du temps
     * @param end Fin d'une contrainte d' emploie du temps
     */
    public Constraint(long id, LocalDate day, LocalTime start, LocalTime end) {
        this.id = id;
        this.day = day;
        this.start = start;
        this.end = end;
        if (id != -1) // To differentiate the ones which are yet in database
            CONSTRAINTS.add(this);
    }

    //Needed for TeacherDao
    /**
     * Constructeur d'une contrainte d' emploie du temps avec un Teacher
     * @param id Identifiant d'une contrainte d' emploie du temps
     * @param day
     * @param start Début d'une contrainte d' emploie du temps
     * @param end Fin d'une contrainte d' emploie du temps
     * @param teacher Identifiant d'un Enseignant
     */
    public Constraint(long id, LocalDate day, LocalTime start, LocalTime end, Teacher teacher) {
        this.id = id;
        this.day = day;
        this.start = start;
        this.end = end;
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

    public void popConstraintInList(Constraint constraint) {
        CONSTRAINTS.remove(constraint);
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
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
        return "Constraint{" +
                "startDate=" + start +
                ", endDate=" + end +
                '}';
    }
}

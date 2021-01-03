package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Constraint implements Entity {
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long teacherID; //needed for dao
    private static final List<Constraint> CONSTRAINTS = new ArrayList<>();

    //Constructors
    public Constraint(long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        if (id != -1) // To differentiate the ones which are yet in database
            CONSTRAINTS.add(this);
    }
    //Needed for TeacherDao
    public Constraint(long id, LocalDateTime startDate, LocalDateTime endDate, long teacherID) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacherID = teacherID;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(long teadherID) {
        this.teacherID = teadherID;
    }

    //Methods
    @Override
    public String toString() {
        return "Constraint{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

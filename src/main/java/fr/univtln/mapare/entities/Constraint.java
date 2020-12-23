package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constraint implements Entity {
    private long id;
    private Date startDate;
    private Date endDate;
    private long teacherID; //needed for dao
    private static final List<Constraint> CONSTRAINTS = new ArrayList<>();

    //Constructors
    public Constraint(long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        if (id != -1) // To differentiate the ones which are yet in database
            CONSTRAINTS.add(this);
    }

    public Constraint(long id, Date startDate, Date endDate, long teacherID) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

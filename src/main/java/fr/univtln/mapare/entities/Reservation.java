package fr.univtln.mapare.entities;

import java.util.Date;

public class Reservation {
    private Date startDate;
    private Date endDate;
    private String label = "";
    private Classroom classroom;
    private Teacher[] teachers;
    private State state;

    //Constructors
    public Reservation(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.classroom = classroom;
        this.teachers = teachers;
        this.state = state;
    }

    public Reservation(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, String label) {
        this(startDate, endDate, classroom, teachers, state);
        this.label = label;
    }

    //Getters & Setters
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public enum State {
        NP, CANCELED, POSTPONED
    }
}

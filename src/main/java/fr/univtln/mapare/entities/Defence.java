package fr.univtln.mapare.entities;

import java.util.Date;

public class Defence extends Reservation{
    private Student student;

    //Constructors
    public Defence(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, Student student) {
        super(startDate, endDate, classroom, teachers, state);
        this.student = student;
    }

    public Defence(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, String label, Student student) {
        super(startDate, endDate, classroom, teachers, state, label);
        this.student = student;
    }

    //Getters & Setters

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

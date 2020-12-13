package fr.univtln.mapare.entities;

import java.util.Date;

public class Defence extends Reservation {
    private Student student;

    //Constructor
    public Defence(Date startDate, Date endDate, String label, String memo, State state, Room room, Teacher[] teachers, Student student) {
        super(startDate, endDate, label, memo, state, room, teachers);
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

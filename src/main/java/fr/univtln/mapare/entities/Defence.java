package fr.univtln.mapare.entities;

import java.util.Date;

public class Defence extends Reservation {
    private Student student;

    //Constructor
    public Defence(long id, Date startDate, Date endDate, String label, String memo, State state, Room room, Student student) {
        super(id, startDate, endDate, label, memo, state, room);
        this.student = student;
    }

    //Getters & Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    //Methods
    @Override
    public String toString() {
        return "Defence{" +
                super.toString() +
                ", student=" + student +
                '}';
    }
}

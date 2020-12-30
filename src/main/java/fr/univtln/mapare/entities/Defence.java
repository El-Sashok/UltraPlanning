package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Defence extends Reservation {
    private Student student;

    //Constructor
    public Defence(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room, Student student) {
        super(id, startDate, endDate, label, memo, state, room);
        this.student = student;
    }

    public Defence(Reservation reservation, Student student) {
        super(reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getLabel(),
                reservation.getMemo(),
                reservation.getState(),
                reservation.getRoom());
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
                ", ID=" + getId() +
                ", student=" + student +
                '}';
    }
}

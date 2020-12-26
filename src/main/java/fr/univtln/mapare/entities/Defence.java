package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Defence extends Reservation {
    private Student student;
    private static List<Defence> DEFENCES = new ArrayList<>();

    //Constructor
    public Defence(long id, Date startDate, Date endDate, String label, String memo, State state, Room room, Student student) {
        super(id, startDate, endDate, label, memo, state, room);
        this.student = student;
        if (id != -1) // To differentiate the ones which are yet in database
            Reservation.popReservationList(this); //TODO check if its working
            DEFENCES.add(this);
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
    public static List<Defence> getDefenceList() {
        return DEFENCES;
    }

    public static void popDefenceInList(Defence defence) {
        DEFENCES.remove(defence);
    }

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

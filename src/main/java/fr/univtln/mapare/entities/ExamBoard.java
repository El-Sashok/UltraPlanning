package fr.univtln.mapare.entities;

import java.time.LocalDateTime;

public class ExamBoard extends Reservation {
    private Yeargroup yeargroup;

    //Constructor
    public ExamBoard(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room, Yeargroup yeargroup) {
        super(id, startDate, endDate, label, memo, state, room);
        this.yeargroup = yeargroup;
    }

    public ExamBoard(Reservation reservation, Yeargroup yeargroup) {
        super(reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getLabel(),
                reservation.getMemo(),
                reservation.getState(),
                reservation.getRoom());
        setManagers(reservation.getManagers());
        this.yeargroup = yeargroup;
    }

    //Getters & Setters
    public Yeargroup getYeargroup() {
        return yeargroup;
    }

    public void setYeargroup(Yeargroup yeargroup) {
        this.yeargroup = yeargroup;
    }

    //Methods


    @Override
    public String toString() {
        return "ExamBoard{" +
                super.toString() +
                ", yeargroup=" + yeargroup +
                '}';
    }
}

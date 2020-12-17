package fr.univtln.mapare.entities;

import java.util.Date;

public class ExamBoard extends Reservation {
    private Yeargroup yeargroup;

    //Constructor
    public ExamBoard(long id, Date startDate, Date endDate, String label, String memo, State state, Room room, Yeargroup yeargroup) {
        super(id, startDate, endDate, label, memo, state, room);
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

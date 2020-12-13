package fr.univtln.mapare.entities;

import java.util.Date;

public class ExamBoard extends Reservation {
    private Yeargroup yeargroup;

    //Constructor
    public ExamBoard(Date startDate, Date endDate, String label, String memo, State state, Room room, Teacher[] teachers, Yeargroup yeargroup) {
        super(startDate, endDate, label, memo, state, room, teachers);
        this.yeargroup = yeargroup;
    }

    //Getters & Setters
    public Yeargroup getYeargroup() {
        return yeargroup;
    }

    public void setYeargroup(Yeargroup yeargroup) {
        this.yeargroup = yeargroup;
    }
}

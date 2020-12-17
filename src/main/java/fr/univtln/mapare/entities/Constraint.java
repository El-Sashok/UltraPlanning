package fr.univtln.mapare.entities;

import java.util.Date;

public class Constraint implements Entity {
    private final long id;
    private Date startDate;
    private Date endDate;

    //Constructors
    public Constraint(long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
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

    //Methods
    @Override
    public String toString() {
        return "Constraint{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

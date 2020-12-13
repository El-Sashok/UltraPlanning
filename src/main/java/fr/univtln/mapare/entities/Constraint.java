package fr.univtln.mapare.entities;

import java.util.Date;

public class Constraint {
    private Date startDate;
    private Date endDate;

    //Constructors
    public Constraint(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
}

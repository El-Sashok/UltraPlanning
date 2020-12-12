package fr.univtln.mapare.entities;

import java.util.Date;

public class Teacher extends Person {
    private String laboratory;
    private Role type;

    //Constructors
    public Teacher(String lastName, String firstName, Date birthdate, String email, String laboratory, Role type) {
        super(lastName, firstName, birthdate, email);
        this.laboratory = laboratory;
        this.type = type;
    }

    //Getters & Setters
    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public Role getType() {
        return type;
    }

    public void setType(Role type) {
        this.type = type;
    }


    public enum Role {
        LECTURER, PROFESSOR, ADJUNCT_PROF
    }
}

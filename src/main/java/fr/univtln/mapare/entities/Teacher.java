package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Teacher extends Person {
    private String laboratory;
    private Role role;
    private List<Constraint> constraints;

    //Constructor
    public Teacher(long id, String lastName, String firstName, Date birthdate, String email, String password, String laboratory, Role role) {
        super(id, lastName, firstName, birthdate, email, password);
        this.laboratory = laboratory;
        this.role = role;
        this.constraints = new ArrayList<Constraint>();
    }

    //Getters & Setters
    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    //Methods
    @Override
    public String toString() {
        return  "Teacher{" +
                super.toString() +
                ", laboratory='" + laboratory + '\'' +
                ", role=" + role +
                ", constraints=" + constraints +
                '}';
    }

    public Teacher addConstraint(Constraint c) {
        constraints.add(c);
        return this;
    }



    public enum Role {
        LECTURER, PROFESSOR, ADJUNCT_PROF
    }
}

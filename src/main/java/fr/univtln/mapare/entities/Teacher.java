package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Teacher extends Person {
    private String laboratory;
    private Role role;
    private List<Constraint> constraints;
    private static final List<Teacher> TEACHERS = new ArrayList<>();
    private static List<Teacher> teacherList = new ArrayList<Teacher>(Arrays.asList(new Teacher[]{new Teacher("Philippe", "LANGEVIN"),
            new Teacher("Laurent-Stéphane", "DIDIER")}));

    public static List<Teacher> getTeacherList() {
        return teacherList;
    }

    //Constructor
    public Teacher(long id, String lastName, String firstName, Date birthdate, String email, String laboratory, Role role) {
        super(id, lastName, firstName, birthdate, email);
        this.laboratory = laboratory;
        this.role = role;
        this.constraints = new ArrayList<Constraint>();
        if (id != -1) // To differentiate the ones which are yet in database
            TEACHERS.add(this);
    }

    private Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    //Getters & Setters
    /*public static List<Teacher> getTeacherList() {
        return TEACHERS;
    }*/

    public static void popTeacherInList(Teacher teacher) {
        TEACHERS.remove(teacher);
    }

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
    /*@Override
    public String toString() {
        return  "Teacher{" +
                super.toString() +
                ", laboratory='" + laboratory + '\'' +
                ", role=" + role +
                ", constraints=" + constraints +
                '}';
    }*/

    public Teacher addConstraint(Constraint c) {
        constraints.add(c);
        return this;
    }



    public enum Role {
        LECTURER, PROFESSOR, ADJUNCT_PROF
    }
}

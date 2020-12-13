package fr.univtln.mapare.entities;

import java.util.Date;

public class Student extends Person {

    //Constructors
    public Student(String lastName, String firstName, Date birthdate, String email) {
        super(lastName, firstName, birthdate, email);
    }

    //Methods

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                '}';
    }
}

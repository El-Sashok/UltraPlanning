package fr.univtln.mapare.entities;

import java.util.Date;

public class Student extends Person {

    //Constructors
    public Student(long id, String lastName, String firstName, Date birthdate, String email, String password) {
        super(id, lastName, firstName, birthdate, email, password);
    }

    //Methods
    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                '}';
    }
}

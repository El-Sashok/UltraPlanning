package fr.univtln.mapare.entities;

import java.util.Date;

public class Student extends Person{
    public Student(String lastName, String firstName, Date birthdate, String email) {
        super(lastName, firstName, birthdate, email);
    }
}

package fr.univtln.mapare.entities;

import java.util.Date;

public class Person {
    private long id = -1;
    private final String lastName;
    private final  String firstName;
    private final Date birthdate;

    public Person(String lastName, String firstName, Date birthdate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}

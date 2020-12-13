package fr.univtln.mapare.entities;

import java.util.Date;

public abstract class Person {
    private final String lastName;
    private final String firstName;
    private final Date birthdate;
    private final String email;

    //Constructor
    public Person(String lastName, String firstName, Date birthdate, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.email = email;
    }

    //Getters & Setters
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    //Methods
    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                '}';
    }
}

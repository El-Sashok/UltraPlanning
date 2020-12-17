package fr.univtln.mapare.entities;

import java.util.Date;

public abstract class Person implements Entity {
    private final long id;
    private final String lastName;
    private final String firstName;
    private final Date birthdate;
    private final String email;
    private final String password;

    //Constructor
    public Person(long id, String lastName, String firstName, Date birthdate, String email, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
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

    @Override
    public long getId() { return id; }

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

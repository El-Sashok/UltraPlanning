package fr.univtln.mapare.entities;

import java.util.Date;
import java.util.Objects;

public abstract class Person implements Entity {
    private long id;
    private final String lastName;
    private final String firstName;
    private final Date birthdate;
    private final String email;

    //Constructor
    protected Person(long id, String lastName, String firstName, Date birthdate, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.email = email;
    }

    protected Person (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        birthdate = null;
        email = null;
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

    @Override
    public void setId(long id) { this.id = id; }

    //Methods
    public String print() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return this.lastName + " " + this.firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

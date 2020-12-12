package fr.univtln.mapare.entities;

import java.util.Date;
import java.util.Objects;

public abstract class Person implements Comparable<Person> {
    private int id = -1;
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

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Person o) {
        return id - o.id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}

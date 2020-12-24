package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }

    public static List<Student> getStudentList() {
        List<Student> placeholder = new ArrayList<Student>();
        placeholder.add(new Student("Nicolas", "MARGUERIT"));
        placeholder.add(new Student("Alexandre", "REAUBOURG"));
        return placeholder;
    }
}

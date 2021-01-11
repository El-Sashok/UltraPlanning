package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private static final List<Student> STUDENTS = new ArrayList<>();

    //Constructors
    public Student(long id, String lastName, String firstName, Date birthdate, String email) {
        super(id, lastName, firstName, birthdate, email);
        if (id != -1) // To differentiate the ones which are yet in database
            STUDENTS.add(this);
    }

    //Getters & Setters
    public static List<Student> getStudentList() {
        return STUDENTS;
    }

    public static void popStudentInList(Student student) {
        STUDENTS.remove(student);
    }

    //Methods
    public String print() {
        return "Student{" +
                super.toString() +
                '}';
    }
}

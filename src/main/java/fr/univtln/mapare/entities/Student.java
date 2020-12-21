package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private static final List<Student> STUDENTS = new ArrayList<>();

    //Constructors
    public Student(long id, String lastName, String firstName, Date birthdate, String email, String password) {
        super(id, lastName, firstName, birthdate, email, password);
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
    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                '}';
    }


}

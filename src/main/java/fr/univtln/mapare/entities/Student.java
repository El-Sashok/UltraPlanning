package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private static final List<Student> STUDENTS = new ArrayList<>();
    public static List<Student> getStudentList() {
        List<Student> placeholder = new ArrayList<Student>();
        placeholder.add(new Student("Nicolas", "MARGUERIT"));
        placeholder.add(new Student("Alexandre", "REAUBOURG"));
        return placeholder;
    }

    //Constructors
    public Student(long id, String lastName, String firstName, Date birthdate, String email) {
        super(id, lastName, firstName, birthdate, email);
        if (id != -1) // To differentiate the ones which are yet in database
            STUDENTS.add(this);
    }

    private Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    //Getters & Setters
    /*public static List<Student> getStudentList() {
        return STUDENTS;
    }*/

    public static void popStudentInList(Student student) {
        STUDENTS.remove(student);
    }

    //Methods
    /*@Override
    public String toString() {
        return "Student{" +
                super.toString() +
                '}';
    }*/
}

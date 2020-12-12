package fr.univtln.mapare.entities;

import java.util.Date;

public class AdmissionExam extends Reservation{
    private Student[] students;

    //Constructors
    public AdmissionExam(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, Student[] students) {
        super(startDate, endDate, classroom, teachers, state);
        this.students = students;
    }

    public AdmissionExam(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, String label, Student[] students) {
        super(startDate, endDate, classroom, teachers, state, label);
        this.students = students;
    }

    //Getters & Setters
    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }
}

package fr.univtln.mapare.entities;

import java.util.Arrays;
import java.util.Date;

public class AdmissionExam extends Reservation {
    private Student[] students;

    //Constructor
    public AdmissionExam(Date startDate, Date endDate, String label, String memo, State state, Room room, Teacher[] teachers, Student[] students) {
        super(startDate, endDate, label, memo, state, room, teachers);
        this.students = students;
    }

    //Getters & Setters
    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    //Methods
    @Override
    public String toString() {
        return "AdmissionExam{" +
                super.toString() +
                ", students=" + Arrays.toString(students) +
                '}';
    }
}

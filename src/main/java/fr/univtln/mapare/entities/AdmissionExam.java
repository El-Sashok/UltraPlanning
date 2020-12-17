package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;

public class AdmissionExam extends Reservation {
    private ArrayList<Student> students;

    //Constructor
    public AdmissionExam(long id, Date startDate, Date endDate, String label, String memo, State state, Room room) {
        super(id, startDate, endDate, label, memo, state, room);
        this.students = new ArrayList<Student>();
    }

    //Getters & Setters
    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    //Methods
    @Override
    public String toString() {
        return "AdmissionExam{" +
                super.toString() +
                ", students=" + students +
                '}';
    }

    public AdmissionExam addStudent(Student s) {
        students.add(s);
        return this;
    }
}

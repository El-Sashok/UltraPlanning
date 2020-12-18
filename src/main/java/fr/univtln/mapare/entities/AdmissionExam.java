package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdmissionExam extends Reservation {
    private List<Student> students;

    //Constructor
    public AdmissionExam(long id, Date startDate, Date endDate, String label, String memo, State state, Room room) {
        super(id, startDate, endDate, label, memo, state, room);
        this.students = new ArrayList<>();
    }

    //Getters & Setters
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
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

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

    private AdmissionExam(String label) {
        super(label);
    }

    //Getters & Setters
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    //Methods
    /*@Override
    public String toString() {
        return "AdmissionExam{" +
                super.toString() +
                ", students=" + students +
                '}';
    }*/
    @Override
    public String toString() {
        return super.toString();
    }

    public static List<AdmissionExam> getAdmissionExamList() {
        List<AdmissionExam> placeholder = new ArrayList<AdmissionExam>();
        placeholder.add(new AdmissionExam("TOEIC"));
        placeholder.add(new AdmissionExam("PIX"));
        return placeholder;
    }

    public AdmissionExam addStudent(Student s) {
        students.add(s);
        return this;
    }
}

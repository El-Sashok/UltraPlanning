package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdmissionExam extends Reservation {
    private AdmissionExamLabel admissionExamLabel;
    private List<Student> students;

    //Constructor
    public AdmissionExam(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room, AdmissionExamLabel admissionExamLabel) {
        super(id, startDate, endDate, label, memo, state, room);
        this.admissionExamLabel = admissionExamLabel;
        this.students = new ArrayList<>();
    }

    public AdmissionExam(Reservation reservation, AdmissionExamLabel admissionExamLabel) {
        super(reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getLabel(),
                reservation.getMemo(),
                reservation.getState(),
                reservation.getRoom());
        setManagers(reservation.getManagers());
        this.admissionExamLabel = admissionExamLabel;
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

    public AdmissionExamLabel getAdmissionExamLabel() {
        return admissionExamLabel;
    }

    public void setAdmissionExamLabel(AdmissionExamLabel admissionExamLabel) {
        this.admissionExamLabel = admissionExamLabel;
    }

    //Methods
    public String print() {
        return "AdmissionExam{" +
                super.print() +
                ", label=" + admissionExamLabel +
                ", students=" + students +
                '}';
    }

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

    public AdmissionExam removeStudent(Student s) {
        students.remove(s);
        return this;
    }
}

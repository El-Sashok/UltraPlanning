package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe entité d'un concours
 * @author Equipe MAPARE
 * @version 1.0
 */
public class AdmissionExam extends Reservation {
    private AdmissionExamLabel admissionExamLabel;
    private List<Student> students;

    //Constructor

    /**
     * Constructeur d'un concours avec tout les paramètre
     * @param id Identifiant d'un concours
     * @param startDate Date et heure du commencement d'un concours
     * @param endDate Date et heure de la fin d'un concours
     * @param label Titre / Étiquette / Annotation du concours
     * @param memo Informations complémentaires d'un concours
     * @param state État d'une réservation
     * @param room Salle dans laquelle se trouve le concours
     * @param admissionExamLabel Intitulé du concours
     */
    public AdmissionExam(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room, AdmissionExamLabel admissionExamLabel) {
        super(id, startDate, endDate, label, memo, state, room);
        this.admissionExamLabel = admissionExamLabel;
        this.students = new ArrayList<>();
    }

    /**
     * Constructeur d'un concours avec une Reservation en paramètre
     * @param reservation Une Reservation
     * @param admissionExamLabel Intitulé du concours
     */
    public AdmissionExam(Reservation reservation, AdmissionExamLabel admissionExamLabel) {
        this(reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getLabel(),
                reservation.getMemo(),
                reservation.getState(),
                reservation.getRoom(),
                admissionExamLabel);
        setManagers(reservation.getManagers());
    }

    //Getters & Setters
    /**
     * @return la liste des étudiants inscrit au concours
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Défini la list des étudiants inscrits au concours
     * @param students Liste d'étudiant
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * @return l'intitulé du concours
     */
    public AdmissionExamLabel getAdmissionExamLabel() {
        return admissionExamLabel;
    }

    /**
     * Défini l'intitulé du concours
     * @param admissionExamLabel Intitulé du concours
     */
    public void setAdmissionExamLabel(AdmissionExamLabel admissionExamLabel) {
        this.admissionExamLabel = admissionExamLabel;
    }

    //Methods
    @Override
    public String print() {
        return "AdmissionExam{" +
                super.print() +
                ", label=" + admissionExamLabel +
                ", students=" + students +
                '}';
    }

    @Override
    public String toString() {
        return admissionExamLabel.getLabel();
    }

    /**
     *  Ajoute un étudiant à la liste des étudiant inscris au concours
     * @param student Un étudiant
     * @return Le concours
     */
    public AdmissionExam addStudent(Student student) {
        students.add(student);
        return this;
    }

    public AdmissionExam removeStudent(Student s) {
        students.remove(s);
        return this;
    }
}

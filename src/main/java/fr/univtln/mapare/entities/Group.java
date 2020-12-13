package fr.univtln.mapare.entities;

public class Group {
    private String label;
    private Student[] students;

    //Constructors
    public Group(String label, Student[] students) {
        this.label = label;
        this.students = students;
    }

    //Getters & Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }
}

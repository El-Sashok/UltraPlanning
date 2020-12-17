package fr.univtln.mapare.entities;

import java.util.ArrayList;

public class Group {
    private String label;
    private ArrayList<Student> students;

    //Constructors
    public Group(String label) {
        this.label = label;
        this.students = new ArrayList<Student>();
    }

    //Getters & Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    //Methods
    @Override
    public String toString() {
        return "Group{" +
                "label='" + label + '\'' +
                ", students=" + students +
                '}';
    }

    public Group addStudent(Student s) {
        students.add(s);
        return this;
    }
}

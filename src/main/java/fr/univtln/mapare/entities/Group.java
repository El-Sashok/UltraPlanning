package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Group implements Entity {
    private final long id;
    private String label;
    private List<Student> students;

    //Constructors
    public Group(long id, String label) {
        this.id = id;
        this.label = label;
        this.students = new ArrayList<Student>();
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
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

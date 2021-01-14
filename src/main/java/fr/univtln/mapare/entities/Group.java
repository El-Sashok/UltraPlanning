package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group implements Entity {
    private long id;
    private String label;
    private List<Student> students;
    private static final List<Group> GROUPS = new ArrayList<>();

    //Constructors
    public Group(long id, String label) {
        this.id = id;
        this.label = label;
        this.students = new ArrayList<>();
        if (id != -1) // To differentiate which ones aren't in the database yet
            GROUPS.add(this);
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static List<Group> getGroupList() {
        return GROUPS;
    }

    public static void popGroupInList(Group group) {
        GROUPS.remove(group);
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

    public String print() {
        return "Group{" +
                "label='" + label + '\'' +
                ", students=" + students +
                '}';
    }
    @Override
    public String toString() {
        return label;
    }

    public Group addStudent(Student s) {
        students.add(s);
        return this;
    }

    public Group removeStudent(Student s) {
        students.remove(s);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
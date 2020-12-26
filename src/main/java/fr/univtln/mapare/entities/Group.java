<<<<<<< HEAD
package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {
    private String label;
    private static List<Group> groupList = new ArrayList<Group>(Arrays.asList(new Group[]{new Group("M1 Info 1"),
            new Group("M1 Info 2")}));

    public Group(String s) {
        label = s;
    }

    public static List<Group> getGroupList() {
        return groupList;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
=======
package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Group implements Entity {
    private long id;
    private String label;
    private List<Student> students;
    private static final List<Group> GROUPS = new ArrayList<>();

    //Constructors
    public Group(long id, String label) {
        this.id = id;
        this.label = label;
        this.students = new ArrayList<Student>();
        if (id != -1) // To differentiate the ones which are yet in database
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
>>>>>>> feature/DAO

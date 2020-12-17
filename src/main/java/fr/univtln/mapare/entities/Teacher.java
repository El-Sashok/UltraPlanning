package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teacher {
    private String firstName;
    private String lastName;
    private static List<Teacher> teacherList = new ArrayList<Teacher>(Arrays.asList(new Teacher[]{new Teacher("Philippe", "LANGEVIN"),
            new Teacher("Laurent-Stéphane", "DIDIER")}));

    public static List<Teacher> getTeacherList() {
        return teacherList;
    }

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

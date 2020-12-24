package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course {
    private String label;
    private static List<Course> courseList = new ArrayList<Course>(Arrays.asList(new Course[]{new Course("Cryptanalyse"),
            new Course("Machine Learning")}));

    public Course(String label) {
        this.label = label;
    }

    public static List<Course> getCourseList() {
        return courseList;
    }

    public Object getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}

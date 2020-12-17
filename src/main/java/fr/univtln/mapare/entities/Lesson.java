package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;

public class Lesson extends Reservation {
    private Type type;
    private ArrayList<Course> courses;
    private ArrayList<Group> groups;

    //Constructor
    public Lesson(Date startDate, Date endDate, String label, String memo, State state, Room room, Type type) {
        super(startDate, endDate, label, memo, state, room);
        this.type = type;
        this.courses = new ArrayList<Course>();
        this.groups = new ArrayList<Group>();
    }

    //Getters & Setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> goups) {
        this.groups = goups;
    }

    //Methods
    @Override
    public String toString() {
        return "Lesson{" +
                super.toString() +
                ", type=" + type +
                ", courses=" + courses +
                ", goups=" + groups +
                '}';
    }

    public Lesson addCourse(Course c) {
        courses.add(c);
        return this;
    }

    public Lesson addGroup(Group g) {
        groups.add(g);
        return this;
    }

    public enum Type {
        TD, TP, CM, CC, CT
    }
}

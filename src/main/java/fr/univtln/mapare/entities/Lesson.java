package fr.univtln.mapare.entities;

import java.util.Date;

public class Lesson extends Reservation {
    private Type type;
    private Course[] courses;
    private Group[] goups;

    //Constructor
    public Lesson(Date startDate, Date endDate, String label, String memo, State state, Room room, Teacher[] teachers, Type type, Course[] courses, Group[] goups) {
        super(startDate, endDate, label, memo, state, room, teachers);
        this.type = type;
        this.courses = courses;
        this.goups = goups;
    }

    //Getters & Setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public Group[] getGoups() {
        return goups;
    }

    public void setGoups(Group[] goups) {
        this.goups = goups;
    }


    public enum Type {
        TD, TP, CM, CC, CT
    }
}

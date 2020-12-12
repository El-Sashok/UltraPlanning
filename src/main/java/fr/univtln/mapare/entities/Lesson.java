package fr.univtln.mapare.entities;

import java.util.Date;

public class Lesson extends Reservation{
    private Type type;
    private Course[] courses;

    //Constructors
    public Lesson(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, Type type, Course[] courses) {
        super(startDate, endDate, classroom, teachers, state);
        this.type = type;
        this.courses = courses;
    }

    public Lesson(Date startDate, Date endDate, Classroom classroom, Teacher[] teachers, State state, String label, Type type, Course[] courses) {
        super(startDate, endDate, classroom, teachers, state, label);
        this.type = type;
        this.courses = courses;
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


    public enum Type {
        TD, TP, CM, CC, CT
    }
}

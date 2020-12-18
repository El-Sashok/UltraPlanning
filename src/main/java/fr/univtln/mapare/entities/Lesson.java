package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Date;

public class Lesson extends Reservation {
    private Type type;
    private ArrayList<Module> cours;
    private ArrayList<Group> groups;

    //Constructor
    public Lesson(long id, Date startDate, Date endDate, String label, String memo, State state, Room room, Type type) {
        super(id, startDate, endDate, label, memo, state, room);
        this.type = type;
        this.cours = new ArrayList<Module>();
        this.groups = new ArrayList<Group>();
    }

    //Getters & Setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<Module> getCourses() {
        return cours;
    }

    public void setCourses(ArrayList<Module> cours) {
        this.cours = cours;
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
                ", courses=" + cours +
                ", goups=" + groups +
                '}';
    }

    public Lesson addCourse(Module c) {
        cours.add(c);
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

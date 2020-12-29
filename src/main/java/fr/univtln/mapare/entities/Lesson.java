package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Lesson extends Reservation {
    private Type type;
    private List<Module> module;
    private List<Group> groups;

    //Constructor
    public Lesson(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room, Type type) {
        super(id, startDate, endDate, label, memo, state, room);
        this.type = type;
        this.module = new ArrayList<Module>();
        this.groups = new ArrayList<Group>();
    }

    public Lesson(Reservation reservation, Type type) {
        super(reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getLabel(),
                reservation.getMemo(),
                reservation.getState(),
                reservation.getRoom());
        this.type = type;
        this.module = new ArrayList<Module>();
        this.groups = new ArrayList<Group>();
    }

    //Getters & Setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Module> getModules() {
        return module;
    }

    public void setCourses(List<Module> module) {
        this.module = module;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    //Methods
    @Override
    public String toString() {
        return "Lesson{" +
                super.toString() +
                ", type=" + type +
                ", courses=" + module +
                ", groups=" + groups +
                '}';
    }

    public Lesson addModule(Module c) {
        module.add(c);
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

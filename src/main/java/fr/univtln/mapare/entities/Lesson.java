package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

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
        setManagers(reservation.getManagers());
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

    public String[] getStringTable() {
        String[] output = new String[9];
        LocalDateTime dateDeb = getStartDate();
        LocalDateTime dateFin = getEndDate();
        List temp = getModules();
        String courseString = temp.get(0).toString();
        if (temp.size() > 1)
            courseString += ", ...";
        temp = getManagers();
        String teacherString = temp.get(0).toString();
        if (temp.size() > 1)
            teacherString += ", ...";
        temp = getGroups();
        System.out.println(temp);
        /*String groupString = temp.get(0).toString();
        if (temp.size() > 1)
            groupString += ", ...";*/
        output[0] = (dateDeb.getDayOfWeek().getValue() - 1) + "";
        output[1] = ((dateDeb.getHour() - 8) * 2 + (dateDeb.getMinute() == 30 ? 1 : 0)) + "";
        output[2] = ((dateFin.getHour() - 9) * 2 + (dateFin.getMinute() == 30 ? 1 : 0)) + "";
        output[3] = courseString;
        output[4] = teacherString;/*
        output[5] = groupString;
        output[6] = comboBox1.getSelectedItem() + "";
        output[7] = comboBox2.getSelectedIndex() + "";
        output[8] = textArea1.getText();*/
        return output;
    }

    public enum Type {
        TD, TP, CM, CC, CT
    }
}

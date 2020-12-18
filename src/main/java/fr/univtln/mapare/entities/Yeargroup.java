package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Yeargroup implements Entity {
    private long id;
    private String label;
    private List<Group> groups;

    //Constructors
    public Yeargroup(long id, String label) {
        this.id = id;
        this.label = label;
        this.groups = new ArrayList<>();
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        return "Yeargroup{" +
                "label='" + label + '\'' +
                ", groups=" + groups +
                '}';
    }

    public Yeargroup addGroup(Group g) {
        groups.add(g);
        return this;
    }
}

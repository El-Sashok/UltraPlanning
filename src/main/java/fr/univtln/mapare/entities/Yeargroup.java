package fr.univtln.mapare.entities;

import java.util.ArrayList;

public class Yeargroup {
    private String label;
    private ArrayList<Group> groups;

    //Constructors
    public Yeargroup(String label) {
        this.label = label;
        this.groups = new ArrayList<Group>();
    }

    //Getters & Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
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
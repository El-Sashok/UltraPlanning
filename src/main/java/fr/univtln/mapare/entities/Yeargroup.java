package fr.univtln.mapare.entities;

public class Yeargroup {
    private String label;
    private Group[] groups;

    //Constructors
    public Yeargroup(String label, Group[] groups) {
        this.label = label;
        this.groups = groups;
    }

    //Getters & Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
    }
}

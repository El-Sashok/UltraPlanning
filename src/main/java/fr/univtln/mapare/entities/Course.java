package fr.univtln.mapare.entities;

public class Course {
    private String label;
    private int nbHour;

    //Constructors
    public Course(String label, int nbHour) {
        this.label = label;
        this.nbHour = nbHour;
    }

    //Getters & Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNbHour() {
        return nbHour;
    }

    public void setNbHour(int nbHour) {
        this.nbHour = nbHour;
    }

    //Methods
    @Override
    public String toString() {
        return "Course{" +
                "label='" + label + '\'' +
                ", nbHour=" + nbHour +
                '}';
    }
}

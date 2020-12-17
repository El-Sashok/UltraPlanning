package fr.univtln.mapare.entities;

public class Course {
    private final long id;
    private String label;
    private int nbHour;

    //Constructors
    public Course(long id, String label, int nbHour) {
        this.id = id;
        this.label = label;
        this.nbHour = nbHour;
    }

    //Getters & Setters
    public long getId() {
        return id;
    }

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

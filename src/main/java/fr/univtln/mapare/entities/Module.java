package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Module implements Entity {
    private final long id;
    private String label;
    private int nbHour;
    private static final List<Module> MODULES = new ArrayList<>();
    //Constructors
    public Module(long id, String label, int nbHour) {
        this.id = id;
        this.label = label;
        this.nbHour = nbHour;
        MODULES.add(this);
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    public static List<Module> getModuleList() { return MODULES; }

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

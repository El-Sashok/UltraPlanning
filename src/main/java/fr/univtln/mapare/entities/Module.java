package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Module implements Entity {
    private long id;
    private String label;
    private int nbHour;
    private static final List<Module> MODULES = new ArrayList<>();

    //Constructors
    public Module(long id, String label, int nbHour) {
        this.id = id;
        this.label = label;
        this.nbHour = nbHour;
        if (id != -1) // To differentiate the ones which are yet in database
            MODULES.add(this);
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

    public static List<Module> getModuleList() { return MODULES; }

    public static void popModuleInList(Module module) {
        MODULES.remove(module);
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
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return id == module.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String print() {
        return "Module{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", nbHour=" + nbHour +
                '}';
    }
}

package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module implements Entity {
    private long id;
    private String label;
    private int nbHour;
    private static final List<Module> MODULES = new ArrayList<>();
    private static List<Module> moduleList = new ArrayList<Module>(Arrays.asList(new Module[]{new Module("Cryptanalyse"),
            new Module("Machine Learning")}));

    //Constructors
    public Module(long id, String label, int nbHour) {
        this.id = id;
        this.label = label;
        this.nbHour = nbHour;
        if (id != -1) // To differentiate the ones which are yet in database
            MODULES.add(this);
    }

    private Module(String label) {
        this.label = label;
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    public static List<Module> getModuleList() {
        return moduleList;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    /*public static List<Module> getModuleList() { return MODULES; }*/

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
}

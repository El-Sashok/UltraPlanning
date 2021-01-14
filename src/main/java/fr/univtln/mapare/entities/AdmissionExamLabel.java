package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe entité d'un intitulé de concours
 * @author Equipe MAPARE
 * @version 1.0
 */
public class AdmissionExamLabel implements Entity {
    private long id;
    private String label;
    private static final List<AdmissionExamLabel> ADMISSIONEXAMLABELS = new ArrayList<>();

    //Constructor

    /**
     * Constructeur d'un intitulé de concours
     * @param id identifiant de l'intitulé de concours
     * @param label Nom de de l'intitulé de concours
     */
    public AdmissionExamLabel(long id, String label) {
        this.id = id;
        this.label = label;
        if (id != -1)
            ADMISSIONEXAMLABELS.add(this);
    }

    //Getters & Setters

    /**
     * @return l'identifiant de l'intitulé de concours
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Défini l'identifiant d'un concours
     * @param id l'identifiant d'un concours
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return La liste de tout les intitulés de concours
     */
    public static List<AdmissionExamLabel> getAdmissionExamLabelList() {
        return ADMISSIONEXAMLABELS;
    }

    /**
     * Supprime un intitulé de la liste de tout les intitulés de concours
     */
    public static void popAdmissionExamLabelInList(AdmissionExamLabel admissionExamLabel) {
        ADMISSIONEXAMLABELS.remove(admissionExamLabel);
    }

    /**
     * @return Le nom d'un intitulé de concours
     */
    public String getLabel() {
        return label;
    }

    /**
     * Défini le nom d'un intitulé de concours
     * @param label Le nom d'un intitulé de concours
     */
    public void setLabel(String label) {
        this.label = label;
    }

    //Methods
    @Override
    public String toString() {
        return label;
    }

    public String print() {
        return "AdmissionExamLabel{" +
                "label=" + label +
                ", id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdmissionExamLabel that = (AdmissionExamLabel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Yeargroup {
    private String label;

    public Yeargroup(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static List<Yeargroup> getYeargroupList() {
        List<Yeargroup> placeholder = new ArrayList<Yeargroup>();
        placeholder.add(new Yeargroup("M1 INFO"));
        placeholder.add(new Yeargroup("M2 INFO"));
        return placeholder;
    }
}

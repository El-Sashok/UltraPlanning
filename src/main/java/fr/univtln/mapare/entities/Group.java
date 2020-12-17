package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {
    private String label;
    private static List<Group> groupList = new ArrayList<Group>(Arrays.asList(new Group[]{new Group("M1 Info 1"),
            new Group("M1 Info 2")}));

    public Group(String s) {
        label = s;
    }

    public static List<Group> getGroupList() {
        return groupList;
    }

    public String getLabel() {
        return label;
    }
}

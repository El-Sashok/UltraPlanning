package fr.univtln.mapare;

import fr.univtln.mapare.controllers.*;
import fr.univtln.mapare.gui.Launcher;

import java.sql.SQLException;


public class App 
{
    public static void main(String[] args) throws SQLException {
        ControllerTools.loadDB();
        Launcher launcher = new Launcher();
        launcher.setVisible(true);
    }
}

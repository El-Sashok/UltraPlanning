package fr.univtln.mapare.controllers;

import java.sql.SQLException;

public abstract class Controllers {
    public static void loadDB() throws SQLException {
        GroupController.loadGroups();
        ModuleController.loadModules();
        RoomController.loadRooms();
    }
}

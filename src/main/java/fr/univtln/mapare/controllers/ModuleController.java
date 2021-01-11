package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ModuleDAO;
import fr.univtln.mapare.entities.Module;


import java.sql.SQLException;

public abstract class ModuleController {

    private ModuleController() {}

    public static void loadModules() throws SQLException {
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.findAll();
        }
    }

    public static void remove(Module module) throws SQLException {
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.remove(module.getId());
        }
        Module.popModuleInList(module);
    }

    public static void createModule(String label, int nbHour) throws SQLException {
        Module module = new Module(-1, label, nbHour);
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            Module newModuleId = moduleDAO.persist(module);
        }
    }
}

package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ModuleDAO;
import fr.univtln.mapare.entities.Module;


import java.sql.SQLException;

public abstract class ModuleController {

    public static void loadModules() throws SQLException {
        ModuleDAO moduleDAO = new ModuleDAO();
        moduleDAO.findAll();
        moduleDAO.close();
    }

    public static void remove(Module module) throws SQLException {
        ModuleDAO moduleDAO = new ModuleDAO();
        moduleDAO.remove(module.getId());
        moduleDAO.close();
        Module.popModuleInList(module);
    }

    public static void createModule(String label, int nbHour) throws SQLException {
        Module module = new Module(-1, label, nbHour);
        ModuleDAO moduleDAO = new ModuleDAO();
        Module newModuleId = moduleDAO.persist(module);
        moduleDAO.close();
    }
}

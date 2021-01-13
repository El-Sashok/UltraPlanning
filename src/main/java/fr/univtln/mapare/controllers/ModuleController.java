package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ModuleDAO;
import fr.univtln.mapare.entities.Module;


import java.sql.SQLException;

public abstract class ModuleController {

    private ModuleController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les modules
     * @throws SQLException Exception SQL
     */
    public static void loadModules() throws SQLException {
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.findAll();
        }
    }

    /**
     * Permet de supprimer un module
     * @param module Le Module à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(Module module) throws SQLException {
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.remove(module.getId());
        }
        Module.popModuleInList(module);
    }

    /**
     * Permet de Créer un module
     * @param label Nom du module
     * @param nbHour Nombre d'heures du module
     * @throws SQLException Exception SQL
     */
    public static void createModule(String label, int nbHour) throws SQLException {
        Module module = new Module(-1, label, nbHour);
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            Module newModuleId = moduleDAO.persist(module);
        }
    }
}

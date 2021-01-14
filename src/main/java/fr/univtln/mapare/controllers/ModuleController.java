package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ModuleDAO;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;


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
     * Permet de créer un module
     * @param label Nom du module
     * @param nbHour Nombre d'heures du module
     * @throws SQLException Exception SQL
     */
    public static void createModule(String label, int nbHour) throws SQLException {
        Module module = new Module(-1, label, nbHour);
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.persist(module);
        }
    }

    /**
     * Permet de changer le nom d'un module
     * @param module Le module
     * @param label Nom du module
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException label est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeLabel(Module module, String label) throws SQLException, EmptyAttributeException, NotChangedException {
        if (label.isEmpty())
            throw new EmptyAttributeException("changeLabel", module);
        if (module.getLabel().equals(label))
            throw new NotChangedException(module);

        module.setLabel(label);
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.update(module);
        }
    }

    /**
     * Permet de changer le nombre d'heures d'un module
     * @param module Le module
     * @param nbHour Le nombre d'heures du module
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeHours(Module module, int nbHour) throws SQLException, NotChangedException {
        if (module.getNbHour() == nbHour)
            throw new NotChangedException(module);

        module.setNbHour(nbHour);
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.update(module);
        }
    }
}

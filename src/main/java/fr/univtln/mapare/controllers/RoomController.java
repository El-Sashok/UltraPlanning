package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;

public abstract class RoomController {

    private RoomController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les salles
     * @throws SQLException Exception SQL
     */
    public static void loadRooms() throws SQLException {
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.findAll();
        }
    }

    /**
     * Permet de supprimer une salle
     * @param room La salle à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(Room room) throws SQLException {
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.remove(room.getId());
        }
        Room.popRoomInList(room);
    }

    /**
     * Permet de créer une nouvelle salle
     * @param building Le bâtiment dans lequel se situe la salle
     * @param number Le numéro de la salle
     * @param capacity Le nombre de personne que peut accueillir la salle
     * @param label Intitulé de la salle
     * @param info Information complémentaires
     * @throws SQLException Exception SQL
     */
    public static void createRoom(String building, int number, int capacity, String label, String info) throws SQLException {
        Room room = new Room(-1, building, number, capacity, label, info);
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.persist(room);
        }
    }

    /**
     * Permet de changer la capacité d'une salle
     * @param room La salle
     * @param capacity Le nombre de places disponibles dans la salle
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeCapacity(Room room, int capacity) throws SQLException, NotChangedException {
        if (room.getCapacity() == capacity)
            throw new NotChangedException(room);

        room.setCapacity(capacity);
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.update(room);
        }
    }

    /**
     * Permet de changer les infos d'une salle (matériel, etc...)
     * @param room La salle
     * @param info Informations sur la salle
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeInfo(Room room, String info) throws SQLException, NotChangedException {
        if (room.getInfo().equals(info))
            throw new NotChangedException(room);

        room.setInfo(info);
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.update(room);
        }
    }

    /**
     * Permet de changer le label d'une salle
     * @param room La salle
     * @param label "Type"/nom donné à la salle (salle informatique, etc...)
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeLabel(Room room, String label) throws SQLException, NotChangedException {
        if (room.getLabel().equals(label))
            throw new NotChangedException(room);

        room.setLabel(label);
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.update(room);
        }
    }
}

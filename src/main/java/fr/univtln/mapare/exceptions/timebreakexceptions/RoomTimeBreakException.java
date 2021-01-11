package fr.univtln.mapare.exceptions.timebreakexceptions;

import fr.univtln.mapare.entities.Room;

public class RoomTimeBreakException extends Exception {
    public RoomTimeBreakException(Room room) {
        super("Room \"" + room.getBuilding() + "." + room.getNumber() + "\" is already used during this time");
    }
}

package fr.univtln.mapare;

import fr.univtln.mapare.controllers.*;
import fr.univtln.mapare.gui.Launcher;

import java.sql.SQLException;


public class App 
{
    /*public static void main( String[] args ) throws SQLException {
        loadEntitiesLists();

        LocalDateTime start = LocalDateTime.of(2020, Month.DECEMBER,30,10,0);
        LocalDateTime end = LocalDateTime.of(2020, Month.DECEMBER,30,11,0);

        ArrayList<Module> modules = new ArrayList<>();
        modules.add(Module.getModuleList().get(0));

        ArrayList<Group> groups = new ArrayList<>();
        groups.add(Group.getGroupList().get(0));

        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.getTeacherList().get(0));

        LessonController.createLesson(start,
                end,
                "label",
                "memo",
                Reservation.State.NP,
                Room.getRoomList().get(1),
                Lesson.Type.CM,
                modules,
                groups,
                teachers);
    }

    private static void loadEntitiesLists() throws SQLException {
        RoomController.loadRooms();
        ModuleController.loadModules();
        GroupController.loadGroups();
        TeacherController.loadTeachers();
        ReservationController.loadReservations();
    }*/

    public static void main(String[] args) throws SQLException {
        ControllerTools.loadDB();
        Launcher launcher = new Launcher();
        launcher.setVisible(true);
    }
}

package fr.univtln.mapare;

import fr.univtln.mapare.controllers.*;

import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, NoSuchAlgorithmException, InterruptedException {
        RoomController.loadRooms();
        ModuleController.loadModules();
        GroupController.loadGroups();
        TeacherController.loadTeachers();

        LocalDateTime start = LocalDateTime.of(2020, Month.DECEMBER,29,10,0);
        LocalDateTime end = LocalDateTime.of(2020, Month.DECEMBER,29,11,0);

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
                Room.getRoomList().get(0),
                Lesson.Type.CM,
                modules,
                groups,
                teachers);
    }
}

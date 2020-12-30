package fr.univtln.mapare;

import fr.univtln.mapare.controllers.*;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.daos.StudentDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, NoSuchAlgorithmException, InterruptedException {
//        RoomController.loadRooms();
//        ModuleController.loadModules();
//        System.out.println(Room.getRoomList());
//        System.out.println(Module.getModuleList());
//        SessionControler.setStatus(SessionControler.Status.MANAGER);

//        Launcher launcher = new Launcher();
//        launcher.setVisible(true);
//        ReservationDAO reservationDAO = new ReservationDAO();
//        Reservation reservation = reservationDAO.find(16).get();
//        System.out.println(reservation.getStartDate().getTime());
        /*
        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.find(1).get();
        roomDAO.close();

        ModuleDAO moduleDAO = new ModuleDAO();
        Module module = moduleDAO.find(1).get();
        moduleDAO.close();

        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.find(2).get();
        groupDAO.close();

        LessonDAO lessonDAO = new LessonDAO();
        Lesson lesson = new Lesson(-1,
                new Date(),
                new Date(),
                "test label",
                "test memo",
                Reservation.State.NP,
                room,
                Lesson.Type.CM);

        lesson.addModule(module);
        lesson.addGroup(group);

        lessonDAO.persist(lesson);
        lessonDAO.close();

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = new Teacher(-1,
                "RAZIK",
                "Joseph",
                new Date(),
                "joseph.razik@univ-tln.fr",
                "motdepasseprotégé",
                "LIS",
                Teacher.Role.PROFESSOR);

        Constraint constraint1 = new Constraint(
                new Date(),
                new Date());
        Constraint constraint2 = new Constraint(
                new Date(),
                new Date());
        teacher.addConstraint(constraint1).addConstraint(constraint2);

        teacherDAO.persist(teacher);
        teacherDAO.close();

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.find(3).get();
        studentDAO.close();


        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group(-1,
                "Master Info - Groupe 1");
        group.addStudent(student);

        groupDAO.persist(group);
        groupDAO.close();

        Yeargroup yeargroup = new Yeargroup(-1,
                "Master Info");
        GroupDAO groupDAO = new GroupDAO();
        List<Group> gps = groupDAO.findAll();
        groupDAO.close();
        for (Group g: gps)
            yeargroup.addGroup(g);
        YeargroupDAO yeargroupDAO = new YeargroupDAO();
        Yeargroup testID = yeargroupDAO.persist(yeargroup);
        yeargroupDAO.close();

        System.out.println(testID.getId()); //test objet bien recréé avec nouvel id
        System.out.println(Yeargroup.getYeargroupList()); //test nouvel objet bien présent dans la liste static
        */


       /* DefenceDAO defenceDAO = new DefenceDAO();
        defenceDAO.findAll();
        Defence defence = defenceDAO.find(56).get();
        for (Reservation r: Reservation.getReservationList())
            System.out.println("in list" + r);
        System.out.println(defence);
        defenceDAO.close();*/


        /*RoomController.loadRooms();
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
                teachers);*/


    }
}

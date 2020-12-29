package fr.univtln.mapare;

import fr.univtln.mapare.controllers.ModuleController;
import fr.univtln.mapare.controllers.RoomController;
import fr.univtln.mapare.controllers.SessionControler;
import fr.univtln.mapare.gui.Launcher;

import fr.univtln.mapare.daos.*;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.DataAccessException;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

        List<Group> groupList = Group.getGroupList2();
        for (Group g : groupList)
            System.out.println(g);
        System.out.println(groupList.size());
    }
}

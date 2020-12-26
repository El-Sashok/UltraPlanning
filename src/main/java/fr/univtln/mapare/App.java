package fr.univtln.mapare;

<<<<<<< HEAD
import fr.univtln.mapare.gui.Launcher;
=======
import fr.univtln.mapare.daos.*;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.DataAccessException;

import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
>>>>>>> feature/DAO

/**
 * Hello world!
 *
 */
public class App 
{
<<<<<<< HEAD
    public static void main( String[] args )
    {
        Launcher launcher = new Launcher();
        launcher.setVisible(true);
=======
    public static void main(String[] args ) throws SQLException {
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

        Teacher teacher = new Teacher(-1,
                "RAZIK",
                "Joseph",
                new Date(),
                "joseph.razik@univ-tln.fr",
                "motdepasseprotégé",
                "LIS",
                Teacher.Role.PROFESSOR);

        //Need to follow the correct order as follows
        Constraint constraint1 = new Constraint(-1,
                new Date(),
                new Date());
        Constraint constraint2 = new Constraint(-1,
                new Date(),
                new Date());
        teacher.addConstraint(constraint1).addConstraint(constraint2);

        TeacherDAO teacherDAO = new TeacherDAO();
        teacher = teacherDAO.persist(teacher);
        teacherDAO.close();


        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.find(3).get();
        studentDAO.close();


        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group(-1,
                "Master Info - Groupe 1");
        group.addStudent(student);

        group = groupDAO.persist(group);
        groupDAO.close();
        System.out.println(group.getId());

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

        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.find(1).get();
        roomDAO.close();

        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = new Reservation(-1,
                new Date(),
                new Date(),
                "test label",
                "test memo",
                Reservation.State.NP,
                room);
        Reservation test = reservationDAO.persist(reservation);
        reservationDAO.close();
        System.out.println(test.getId());
        */

        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> rs = reservationDAO.findAll();
        reservationDAO.close();

        System.out.println(rs.size());
>>>>>>> feature/DAO
    }
}
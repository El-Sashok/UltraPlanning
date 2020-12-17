package fr.univtln.mapare;

import fr.univtln.mapare.daos.StudentDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.DataAccessException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) throws IOException, DataAccessException {

        StudentDAO studentDAO = new StudentDAO();

        System.out.println(studentDAO.findAll());

        //Defines constraint
        //Constraint[] c1 = {new Constraint(new Date(), new Date())};
        //Creates persons
//        Teacher teacher1 = new Teacher("Marley",
//                "Bob",
//                new Date(1945, 2, 6),
//                "bobm@gmail.com",
//                "marijuana",
//                Teacher.Role.ADJUNCT_PROF,
//                c1);
//        Teacher teacher2 = new Teacher("Rémi",
//                        "Gaillard",
//                        new Date(1975, 2, 7),
//                        "remg@gmail.com",
//                        "youtube",
//                        Teacher.Role.LECTURER,
//                        c1);
//        Teacher[] teachers1 = {teacher1, teacher2};
//        Student student1 = new Student("Réaubourg",
//                "Alexandre",
//                new Date(),
//                "sasha@gmail.com");
//        Student student2 = new Student("Palma",
//                "François",
//                new Date(),
//                "フランソワ@gmail.com");
//        Student[] students1 = {student1, student2};
        //Creates a group
//        Group g1 = new Group("Pas ouf", students1);
//        //Creates a reservation
//        Reservation r1 = new Reservation(new Date(), new Date(), "NO IDEA", "NO IDEA 2", Reservation.State.NP, new Room("U1", 111, 15, "Salle info", "3 pc en panne"), teachers1);
//        System.out.println();
//        System.out.println(r1.getRoom());
//        System.out.println(teacher1);
//        System.out.println(g1);
//        System.out.println(r1);

    }
}

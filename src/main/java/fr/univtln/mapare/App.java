package fr.univtln.mapare;

import fr.univtln.mapare.entities.*;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //Defines constraint
        Constraint c1 = new Constraint(new Date(), new Date());
        //Creates persons
        Teacher teacher1 = new Teacher("Marley",
                "Bob",
                new Date(1945, 2, 6),
                "bobm@gmail.com",
                "marijuana",
                Teacher.Role.ADJUNCT_PROF);
        Teacher teacher2 = new Teacher("Rémi",
                        "Gaillard",
                        new Date(1975, 2, 7),
                        "remg@gmail.com",
                        "youtube",
                        Teacher.Role.LECTURER);
        Student student1 = new Student("Réaubourg",
                "Alexandre",
                new Date(),
                "sasha@gmail.com");
        Student student2 = new Student("Palma",
                "François",
                new Date(),
                "フランソワ@gmail.com");
        teacher1.addConstraint(c1);
        teacher2.addConstraint(c1);
        //Creates a group
        Group g1 = new Group("Pas ouf");
        g1.addStudent(student1).addStudent(student2);
        //Creates a reservation
        Reservation r1 = new Reservation(new Date(),
                new Date(),
                "NO IDEA",
                "NO IDEA 2",
                Reservation.State.NP,
                new Room("U1", 111, 15, "Salle info", "3 pc en panne"));
        r1.addTeacher(teacher1).addTeacher(teacher2);
        //Creates a admission exam
        AdmissionExam a1 = new AdmissionExam(new Date(),
                new Date(),
                "NO IDEA3",
                "NO IDEA4",
                Reservation.State.NP,
                new Room("U1", 111, 15, "Salle info", "3 pc en panne"));
        a1.addStudent(student1).addStudent(student2).addTeacher(teacher1).addTeacher(teacher2);
    }
}

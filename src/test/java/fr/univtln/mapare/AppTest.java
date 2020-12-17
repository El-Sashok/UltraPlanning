package fr.univtln.mapare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import fr.univtln.mapare.entities.*;
import org.junit.Test;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    //Defines constraint
    Date date = new Date();
    Constraint c1 = new Constraint(date, date);
    //Creates persons
    Teacher teacher1 = new Teacher("Marley",
            "Bob",
            new Date(1945, 2, 6),
            "bobm@gmail.com",
            "marijuana",
            Teacher.Role.ADJUNCT_PROF);
    Student student1 = new Student("Réaubourg",
            "Alexandre",
            new Date(),
            "sasha@gmail.com");
    Student student2 = new Student("Palma",
            "François",
            new Date(),
            "フランソワ@gmail.com");
    //Creates reservation
    Reservation r1 = new Reservation(new Date(),
            new Date(),
            "NO IDEA",
            "NO IDEA 2",
            Reservation.State.NP,
            new Room("U1", 111, 15, "Salle info", "3 pc en panne"));
    //Creates a admission exam
    AdmissionExam a1 = new AdmissionExam(new Date(),
            new Date(),
            "NO IDEA3",
            "NO IDEA4",
            Reservation.State.NP,
            new Room("U1", 111, 15, "Salle info", "3 pc en panne"));
    @Test
    public void testCreationCronstraint() {
        assertEquals(c1.getStartDate(), date);
        assertEquals(c1.getEndDate(), date);
    }
    @Test
    public void testCreationTeacher() {
        assertEquals(teacher1.getFirstName(), "Bob");
    }
    @Test
    public void testCeationStudents() {
        assertEquals(student1.getEmail(), "sasha@gmail.com");
        assertEquals(student2.getEmail(), "フランソワ@gmail.com");
    }
    @Test
    public void testCreationReservation() {
        assertEquals(r1.getLabel(), "NO IDEA");
    }
    @Test
    public void testCreationAdmisionExam() {
        assertEquals(a1.getLabel(), "NO IDEA3");
    }
    @Test
    public void testAjoutDivers() {
        assertEquals(teacher1.addConstraint(c1).getConstraints(), teacher1.getConstraints());
        assertEquals(r1.addTeacher(teacher1).getTeachers(), r1.getTeachers());
        assertEquals(a1.addStudent(student1).addStudent(student2).getStudents(), a1.getStudents());
        assertEquals(a1.addTeacher(teacher1).getTeachers(), a1.getTeachers());
    }
}

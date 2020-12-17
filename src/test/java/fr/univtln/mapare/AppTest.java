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
    @Test
    public void testCreationCrontrainte() {
        assertEquals(c1.getStartDate(), date);
        assertEquals(c1.getEndDate(), date);
    }
    @Test
    public void testCreationTeacher() {
        assertEquals(teacher1.getFirstName(), "Bob");
    }
    @Test
    public void testCeationStudent() {
        assertEquals(student1.getEmail(), "sasha@gmail.com");
    }
}

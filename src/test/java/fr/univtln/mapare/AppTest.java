package fr.univtln.mapare;

import fr.univtln.mapare.controllers.SessionController;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    //Defines constraint
    Constraint c1 = new Constraint(-1, LocalDate.now(), LocalTime.now(), LocalTime.now());

    //Creates persons
    Teacher teacher1 = new Teacher(
            123,
            "Marley",
            "Bob",
            new Date(1945, 2, 6),
            "bobm@gmail.com",
            "marijuana",
            Teacher.Role.ADJUNCT_PROF);

    Student student1 = new Student(
            125,
            "Réaubourg",
            "Alexandre",
            new Date(2010, 5, 15),
            "sasha@gmail.com");
    Student student2 = new Student(
            165,
            "Palma",
            "François",
            new Date(),
            "フランソワ@gmail.com");

    //Creates a room
    Room room = new Room(2, "U1", 111, 15, "Salle info", "3 pc en panne");

    //Creates groups
    Group g1 = new Group(1, "groupe 1");
    Group g2 = new Group(2, "groupe 2");

    //creates a yeargroup
    Yeargroup yg = new Yeargroup(1, "Master 1 Informatique");

    //creates a module
    Module m = new Module(1, "I143", 80);

    //Creates reservation
    LocalDateTime dateTime = LocalDateTime.now();

    Reservation r1 = new Reservation(
            3,
            dateTime,
            dateTime,
            "NO IDEA",
            "NO IDEA2",
            Reservation.State.NP,
            room);

    //Creates an admissionExamLabel
    AdmissionExamLabel admissionExamLabel = new AdmissionExamLabel(-1, "TOEIC");

    //Creates an admissionExam
    AdmissionExam a1 = new AdmissionExam(
            4,
            dateTime,
            dateTime,
            "NO IDEA3",
            "NO IDEA4",
            Reservation.State.NP,
            room,
            admissionExamLabel);

    //Creates a Lesson
    Lesson l1 = new Lesson(5,
            dateTime,
            dateTime,
            "NO IDEA5",
            "NO IDEA6",
            Reservation.State.POSTPONED,
            room,
            Lesson.Type.CT);

    //Creates an ExamBoard
    ExamBoard e1 = new ExamBoard(6,
            dateTime,
            dateTime,
            "NO IDEA7",
            "NO IDEA8",
            Reservation.State.NP,
            room,
            yg);

    //creates a Defence
    Defence d1 = new Defence(7,
            dateTime,
            dateTime,
            "NO IDEA9",
            "NO IDEA10",
            Reservation.State.NP,
            room,
            student2);

    //creates a session
    Session session = new Session((long) 55, "test", "passwordhashé", Session.Status.INVITE);

    @Test
    public void testSession() {
        assertEquals(session.getId(), 55);
        assertEquals(session.getLogin(), "test");
        assertEquals(session.getHashedPassword(), "passwordhashé");
    }

    @Test
    public void testCreationConstraint() {
        assertEquals(c1.getDayOfTheWeek().getDayOfWeek(), LocalDate.now().getDayOfWeek());
        assertEquals(c1.getStartHour().withNano(0), LocalTime.now().withNano(0));
        assertEquals(c1.getEndHour().withNano(0), LocalTime.now().withNano(0));
    }

    @Test
    public void testCreationTeacher() {
        assertEquals(teacher1.getId(), 123);
        assertEquals(teacher1.getLastName(), "Marley");
        assertEquals(teacher1.getFirstName(), "Bob");
        assertEquals(teacher1.getBirthdate(), new Date(1945, 2, 6));
        assertEquals(teacher1.getEmail(), "bobm@gmail.com");
        assertEquals(teacher1.getLaboratory(), "marijuana");
        assertEquals(teacher1.getRole(), Teacher.Role.ADJUNCT_PROF);
        assertNotEquals(new ArrayList<Constraint>(teacher1.getConstraints()), teacher1.addConstraint(c1).getConstraints());
    }

    @Test
    public void testCreationStudent() {
        assertEquals(student1.getId(), 125);
        assertEquals(student1.getLastName(), "Réaubourg");
        assertEquals(student1.getFirstName(), "Alexandre");
        assertEquals(student1.getBirthdate(), new Date(2010, 5, 15));
        assertEquals(student1.getEmail(), "sasha@gmail.com");
    }

    @Test
    public void testCreationGroup() {
        assertEquals(g1.getId(), 1);
        assertEquals(g1.getLabel(), "groupe 1");
        assertNotEquals(new ArrayList<Student>(g1.getStudents()), g1.addStudent(student1).getStudents());
    }

    @Test
    public void testCreationYeargroup() {
        assertEquals(yg.getId(), 1);
        assertEquals(yg.getLabel(), "Master 1 Informatique");
        assertNotEquals(new ArrayList<Group>(yg.getGroups()), yg.addGroup(g1).addGroup(g2).getGroups());
    }

    @Test
    public void testCreationRoom() {
        assertEquals(room.getId(), 2);
        assertEquals(room.getBuilding(), "U1");
        assertEquals(room.getNumber(), 111);
        assertEquals(room.getCapacity(), 15);
        assertEquals(room.getLabel(), "Salle info");
        assertEquals(room.getInfo(), "3 pc en panne");
    }

    @Test
    public void testCreationModule() {
        assertEquals(m.getId(), 1);
        assertEquals(m.getLabel(), "I143");
        assertEquals(m.getNbHour(), 80);
    }

    @Test
    public void testCreationAdmissionExamLabel() {
        assertEquals(admissionExamLabel.getId(), -1);
        assertEquals(admissionExamLabel.getLabel(), "TOEIC");
    }

    @Test
    public void testCreationReservation() {
        assertEquals(r1.getId(), 3);
        assertEquals(r1.getStartDate(), dateTime);
        assertEquals(r1.getEndDate(), dateTime);
        assertEquals(r1.getLabel(), "NO IDEA");
        assertEquals(r1.getMemo(), "NO IDEA2");
        assertEquals(r1.getState(), Reservation.State.NP);
        assertEquals(r1.getRoom(), room);
        assertNotEquals(new ArrayList<Teacher>(r1.getManagers()), r1.addTeacher(teacher1).getManagers());
    }

    @Test
    public void testCreationAdmissionExam() {
        assertEquals(a1.getId(), 4);
        assertEquals(a1.getStartDate(), dateTime);
        assertEquals(a1.getEndDate(), dateTime);
        assertEquals(a1.getLabel(), "NO IDEA3");
        assertEquals(a1.getMemo(), "NO IDEA4");
        assertEquals(a1.getState(), Reservation.State.NP);
        assertEquals(a1.getRoom(), room);
        assertNotEquals(new ArrayList<Teacher>(a1.getManagers()), a1.addTeacher(teacher1).getManagers());
        assertNotEquals(new ArrayList<Student>(a1.getStudents()), a1.addStudent(student1).addStudent(student2).getStudents());
        assertEquals(a1.getAdmissionExamLabel(), admissionExamLabel);
    }

    @Test
    public void testCreationLesson() {
        assertEquals(l1.getId(), 5);
        assertEquals(l1.getStartDate(), dateTime);
        assertEquals(l1.getEndDate(), dateTime);
        assertEquals(l1.getLabel(), "NO IDEA5");
        assertEquals(l1.getMemo(), "NO IDEA6");
        assertEquals(l1.getState(), Reservation.State.POSTPONED);
        assertEquals(l1.getRoom(), room);
        assertEquals(l1.getType(), Lesson.Type.CT);
        assertNotEquals(new ArrayList<Teacher>(l1.getManagers()), l1.addTeacher(teacher1).getManagers());
        assertNotEquals(new ArrayList<Group>(l1.getGroups()), l1.addGroup(g1).addGroup(g2).getGroups());
        assertNotEquals(new ArrayList<Module>(l1.getModules()), l1.addModule(m).getModules());
    }

    @Test
    public void testCreationExamBoard() {
        assertEquals(e1.getId(), 6);
        assertEquals(e1.getStartDate(), dateTime);
        assertEquals(e1.getEndDate(), dateTime);
        assertEquals(e1.getLabel(), "NO IDEA7");
        assertEquals(e1.getMemo(), "NO IDEA8");
        assertEquals(e1.getState(), Reservation.State.NP);
        assertEquals(e1.getRoom(), room);
        assertEquals(e1.getYeargroup(), yg);
        assertNotEquals(new ArrayList<Teacher>(e1.getManagers()), e1.addTeacher(teacher1).getManagers());
    }

    @Test
    public void testCreationDefence() {
        assertEquals(d1.getId(), 7);
        assertEquals(d1.getStartDate(), dateTime);
        assertEquals(d1.getEndDate(), dateTime);
        assertEquals(d1.getLabel(), "NO IDEA9");
        assertEquals(d1.getMemo(), "NO IDEA10");
        assertEquals(d1.getState(), Reservation.State.NP);
        assertEquals(d1.getRoom(), room);
        assertEquals(d1.getStudent(), student2);
        assertNotEquals(new ArrayList<Teacher>(d1.getManagers()), d1.addTeacher(teacher1).getManagers());
    }

    @Test
    public void testEqualsPerson() {
        Person personTest = new Person(0, "Marguerit", "Nicolas", new Date(), "nico@nico.fr") {};

        assertNotEquals(personTest, teacher1);
        personTest.setId(teacher1.getId());
        assertEquals(personTest, teacher1);
        assertNotEquals(new Group(teacher1.getId(), "test"), teacher1);

        assertNotEquals(personTest, student1);
        personTest.setId(student1.getId());
        assertEquals(personTest, student1);
        assertNotEquals(new Group(student1.getId(), "test"), student1);
    }

    @Test
    public void testEqualsReservation() {
        Reservation resaTest = new Reservation(0, dateTime, dateTime, "NO IDEA", "NO IDEA10", Reservation.State.NP, room);
        AdmissionExam aTest = new AdmissionExam(0, dateTime, dateTime, "NO IDEA", "NO IDEA10", Reservation.State.NP, room, admissionExamLabel);
        ExamBoard eTest = new ExamBoard(0, dateTime, dateTime, "NO IDEA", "NO IDEA10", Reservation.State.NP, room, yg);
        Lesson lTest = new Lesson(0, dateTime, dateTime, "NO IDEA", "NO IDEA10", Reservation.State.NP, room, Lesson.Type.CT);
        Defence dTest = new Defence(0, dateTime, dateTime, "NO IDEA", "NO IDEA10", Reservation.State.NP, room, student2);

        assertNotEquals(resaTest, r1);
        resaTest.setId(r1.getId());
        assertEquals(resaTest, r1);
        assertNotEquals(new Group(r1.getId(), "test"), r1);

        assertNotEquals(aTest, a1);
        aTest.setId(a1.getId());
        assertEquals(aTest, a1);
        assertNotEquals(new Group(a1.getId(), "test"), a1);

        assertNotEquals(eTest, e1);
        eTest.setId(e1.getId());
        assertEquals(eTest, e1);
        assertNotEquals(new Group(e1.getId(), "test"), e1);

        assertNotEquals(lTest, l1);
        lTest.setId(l1.getId());
        assertEquals(lTest, l1);
        assertNotEquals(new Group(l1.getId(), "test"), l1);

        assertNotEquals(dTest, d1);
        dTest.setId(d1.getId());
        assertEquals(dTest, d1);
        assertNotEquals(new Group(d1.getId(), "test"), d1);
    }

    @Test
    public void testEqualsRoom() {
        Room roomTest = new Room(0, "U1", 111, 15, "Salle info", "3 pc en panne");

        assertNotEquals(roomTest, room);
        roomTest.setId(room.getId());
        assertEquals(roomTest, room);
        assertNotEquals(new Group(room.getId(), "test"), room);
    }

    @Test
    public void testHashPassword() throws NoSuchAlgorithmException {
        Session.login(session.getId(), session.getLogin(), session.getHashedPassword(), session.getStatus());

        Throwable exception1 = assertThrows(IncorrectPasswordException.class, () -> {
            SessionController.checkPassword("motdepasseprotégé");
        });
        assertTrue(exception1.getMessage().contains("Incorrect password"));
    }
}

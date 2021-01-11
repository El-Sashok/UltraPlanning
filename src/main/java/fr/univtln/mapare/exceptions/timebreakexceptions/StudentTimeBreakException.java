package fr.univtln.mapare.exceptions.timebreakexceptions;

import fr.univtln.mapare.entities.Student;

public class StudentTimeBreakException extends Exception {
    public StudentTimeBreakException(Student student) {
        super("Student \"" + student.getFirstName() + " " + student.getLastName() + "\" is already busy during this time");
    }
}

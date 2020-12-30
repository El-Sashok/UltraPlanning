package fr.univtln.mapare.exceptions.TimeBreakExceptions;

import fr.univtln.mapare.entities.Student;

public class StudentTimeBreakException extends RuntimeException {
    public StudentTimeBreakException(Student student) {
        super("Student \"" + student.getFirstName() + " " + student.getLastName() + "\" is already busy during this time");
    }
}

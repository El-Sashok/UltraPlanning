package fr.univtln.mapare.exceptions;

public class EmptySelectionListException extends Exception{

    public EmptySelectionListException() {
        super();
    }

    public EmptySelectionListException(String message) {
        super(message);
    }
}

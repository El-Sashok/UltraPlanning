package fr.univtln.mapare.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation implements Entity {
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String label;
    private String memo;
    private State state;
    private Room room;
    private List<Teacher> teachers;
    private static final List<Reservation> RESERVATIONS = new ArrayList<>();

    //Constructor
    public Reservation(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.label = label;
        this.memo = memo;
        this.state = state;
        this.room = room;
        this.teachers = new ArrayList<Teacher>();
        if (id != -1) // To differentiate the ones which are yet in database
            RESERVATIONS.add(this);
    }

    //Note: delete later, used for tests
    protected Reservation(String label) {
        this.label = label;
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static List<Reservation> getReservationList() { return RESERVATIONS; }

    public static void popReservationList(Reservation reservation) {
        RESERVATIONS.remove(reservation);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    //Methods
    /*@Override
    public String toString() {
        return "Reservation{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", label='" + label + '\'' +
                ", memo='" + memo + '\'' +
                ", state=" + state +
                ", room=" + room +
                ", teachers=" + teachers +
                '}';
    }*/

    @Override
    public String toString() {
        return label;
    }

    public Reservation addTeacher(Teacher t) {
        teachers.add(t);
        return this;
    }


    public enum State {
        NP, CANCELED, POSTPONED
    }
}

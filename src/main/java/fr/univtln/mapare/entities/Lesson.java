package fr.univtln.mapare.entities;

import fr.univtln.mapare.controllers.ControllerTools;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Lesson extends Reservation {
    private Type type;
    private List<Module> module;
    private List<Group> groups;

    //Constructor
    public Lesson(long id, LocalDateTime startDate, LocalDateTime endDate, String label, String memo, State state, Room room, Type type) {
        super(id, startDate, endDate, label, memo, state, room);
        this.type = type;
        this.module = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public Lesson(Reservation reservation, Type type) {
        this(reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getLabel(),
                reservation.getMemo(),
                reservation.getState(),
                reservation.getRoom(),
                type);
        setManagers(reservation.getManagers());
    }

    //Getters & Setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Module> getModules() {
        return module;
    }

    public void setCourses(List<Module> module) {
        this.module = module;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    //Methods
    @Override
    public String toString() {
        return "Lesson{" +
                super.toString() +
                ", type=" + type +
                ", courses=" + module +
                ", groups=" + groups +
                '}';
    }

    public Lesson addModule(Module c) {
        module.add(c);
        return this;
    }

    public Lesson removeModule(Module c) {
        module.remove(c);
        return this;
    }

    public Lesson addGroup(Group g) {
        groups.add(g);
        return this;
    }

    public Lesson removeGroup(Module c) {
        module.remove(c);
        return this;
    }

    public enum Type {
        TD, CM, TP, CC, CT
    }

    public static Map<Integer, List<Lesson>> getLessonsForWeek(int weekOfYear){
        Map<Integer, List<Lesson>> reservationsForWeek = new HashMap<>();
        for (int i = 0 ; i < 6 ; i++){
            reservationsForWeek.put(i, new ArrayList<>());
        }

        for (Reservation r : Reservation.getReservationList()){
            if (r instanceof Lesson) {
                LocalDateTime date = r.getStartDate();
                Calendar calendar = Calendar.getInstance(Locale.FRANCE);
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                if (weekOfYear == calendar.get(Calendar.WEEK_OF_YEAR)){
                    reservationsForWeek.get(calendar.get(Calendar.DAY_OF_WEEK) - 2).add((Lesson) r);
                }
            }
        }
        return reservationsForWeek;
    }

    public static List<Lesson> getLessonsForDay(int dayOfYear){
        List<Lesson> reservationForWeek = new ArrayList<>();
        for (Reservation r : Reservation.getReservationList()){
            if (r instanceof Lesson) {
                LocalDateTime date = r.getStartDate();
                Calendar calendar = Calendar.getInstance(Locale.FRANCE);
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                if (dayOfYear == calendar.get(Calendar.DAY_OF_YEAR)){
                    reservationForWeek.add((Lesson) r);
                }
            }
        }
        return reservationForWeek;
    }
}

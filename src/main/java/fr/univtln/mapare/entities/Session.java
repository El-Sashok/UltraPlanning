package fr.univtln.mapare.entities;

public class Session implements Entity{
    private static Long id;
    private static Status status;
    private static String login;
    private static String hashedPassword;
    private static Session instance;

    public Session(Long id, String login, String hashedPassword, Status status) {
        Session.id = id;
        Session.login = login;
        Session.hashedPassword = hashedPassword;
        Session.status = status;
        Session.instance = this;
    }

    public static Status getStatus() { return Session.status; }

    public static void setStatus(Status status){
        Session.status = status;
    }

    public static String getLogin() { return login; }

    public static void setLogin(String login) { Session.login = login; }

    public static String getHashedPassword() { return hashedPassword; }

    public static void setHashedPassword(String hashedPassword) { Session.hashedPassword = hashedPassword; }

    public static Session getInstance(){ return Session.instance; }

    @Override
    public long getId() { return Session.id; }

    @Override
    public void setId(long id) { Session.id = id; }


    public enum Status {
        STUDENT, TEACHER, MANAGER, ADMIN
    }
}

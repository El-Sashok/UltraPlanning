package fr.univtln.mapare.entities;

public class Session implements Entity{
    private Long id;
    private Status status;
    private String login;
    private String hashedPassword;
    private static Session instance = null;

    private Session(Long id, String login, String hashedPassword, Status status) {
        this.id = id;
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.status = status;
    }

    public static void login(Long id, String login, String hashedPassword, Status status) {
        instance = new Session(id, login, hashedPassword, status);
    }

    public static void logout() {
        instance = null;
    }

    public static Status getStatus() { return instance.status; }

    public static void setStatus(Status status){
        instance.status = status;
    }

    public static String getLogin() { return instance.login; }

    public static void setLogin(String login) { instance.login = login; }

    public static String getHashedPassword() { return instance.hashedPassword; }

    public static void setHashedPassword(String hashedPassword) { instance.hashedPassword = hashedPassword; }

    public static Session getInstance(){ return instance; }

    @Override
    public long getId() { return instance.id; }

    @Override
    public void setId(long id) { instance.id = id; }


    public enum Status {
        STUDENT, TEACHER, MANAGER, ADMIN, INVITE
    }
}

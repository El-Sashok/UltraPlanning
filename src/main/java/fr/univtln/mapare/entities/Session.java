package fr.univtln.mapare.entities;

public class Session implements Entity{
    private Long id;
    private Status status;
    private String login;
    private String hashedPassword;
    private static Session instance = null;

    public Session(Long id, String login, String hashedPassword, Status status) {
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

    public Status getStatus() { return this.status; }

    public void setStatus(Status status){ this.status = status; }

    public String getLogin() { return this.login; }

    public void setLogin(String login) { this.login = login; }

    public String getHashedPassword() { return this.hashedPassword; }

    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    public static Session getInstance(){ return instance; }

    @Override
    public long getId() { return this.id; }

    @Override
    public void setId(long id) { this.id = id; }


    public enum Status {
        STUDENT, TEACHER, MANAGER, ADMIN, INVITE
    }
}

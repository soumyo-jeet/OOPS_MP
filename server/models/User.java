package models;
import java.util.*;

public class User {
    private int id;
    private String username;
    private String pass;
    private List<Diary> diaries;


    public User(String username, String pass) {
        this.username = username;
        this.pass = pass;
        this.diaries = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public List<Diary> getDiaries() {
        return diaries;
    }
    public void addDiaries(Diary diary) {
        this.diaries.add(diary);
    }

    @Override
    public String toString () {
        return """
                User:
                id: %d,
                username: %s,
                password: %s,
                diaries: %d
                """.formatted(this.id, this.username, this.pass, this.diaries.size());
    }
}
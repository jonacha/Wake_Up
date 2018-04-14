package pract.es.deusto.wake_up.entidades;

/**
 * Created by Jon_Acha on 30/03/2018.
 */

public class Enfermera {
    private Integer id;
    private String nick;
    private String email;
    private String pass;

    public Enfermera(Integer id, String nick, String email, String pass) {
        this.id = id;
        this.nick = nick;
        this.email = email;
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

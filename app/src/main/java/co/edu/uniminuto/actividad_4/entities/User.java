package co.edu.uniminuto.actividad_4.entities;

public class User {
    private int document;
    private String name;
    private String lastname;
    private String user;

    private String pass;

    public User() {
    }

    public User(int document, String name,String lastname, String user,  String pass) {
        this.document = document;
        this.name = name;
        this.lastname = lastname;
        this.user = user;

        this.pass = pass;
    }




    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User : ");
        sb.append("document=").append(document);
        sb.append(", name='").append(name).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", pass='").append(pass).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

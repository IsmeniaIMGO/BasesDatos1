package src.model;

public class Cuenta {
    private String correo;
    private String user;
    private String password;

    public Cuenta() {}

    public Cuenta(String correo, String user, String password) {
        this.correo = correo;
        this.user = user;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

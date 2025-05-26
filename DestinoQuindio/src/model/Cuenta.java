package src.model;

public class Cuenta {
    private String correo;
    private String usuario;
    private String contrasena;

    public Cuenta() {}

    public Cuenta(String correo, String usuario, String contrasena) {
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getcontrasena() {
        return contrasena;
    }

    public void setcontrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

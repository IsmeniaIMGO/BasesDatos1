package src.model;

public class Usuario {
    private int cc;
    private String nombre;
    private String telefono;
    private int nivel; // 1: Admin, 2: Paramétrico, 3: Esporádico
    private String cuenta; // correo
    private int historialSesion;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int cc, String nombre, String telefono, int nivel, String cuenta, int historialSesion) {
        this.cc = cc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nivel = nivel;
        this.cuenta = cuenta;
        this.historialSesion = historialSesion;
    }

    // Getters y Setters
    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getHistorialSesion() {
        return historialSesion;
    }

    public void setHistorialSesion(int historialSesion) {
        this.historialSesion = historialSesion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cc=" + cc +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nivel=" + nivel +
                ", cuenta='" + cuenta + '\'' +
                ", historialSesion=" + historialSesion +
                '}';
    }
}

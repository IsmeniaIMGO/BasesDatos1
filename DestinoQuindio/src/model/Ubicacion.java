package src.model;

public class Ubicacion {
    private int id;
    private double longitud;
    private double latitud;
    private int municipio;

    public Ubicacion() {}

    public Ubicacion(int id, double longitud, double latitud, int municipio) {
        this.id = id;
        this.longitud = longitud;
        this.latitud = latitud;
        this.municipio = municipio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }
}

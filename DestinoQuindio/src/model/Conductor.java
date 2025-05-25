package src.model;

public class Conductor {
    private int cc;
    private String vehiculo; // placa

    public Conductor() {}

    public Conductor(int cc, String vehiculo) {
        this.cc = cc;
        this.vehiculo = vehiculo;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
}

package src.model;

import java.util.Date;

public class Viaje {
    private int id;
    private Date fecha;
    private int pago;
    private int cliente;
    private int conductor;
    private int ubicacionOrigen;
    private int ubicacionDestino;
    private int estado;
    private String vehiculo;

    public Viaje() {}

    public Viaje(int id, Date fecha, int pago, int cliente, int conductor, int ubicacionOrigen,
                 int ubicacionDestino, int estado, String vehiculo) {
        this.id = id;
        this.fecha = fecha;
        this.pago = pago;
        this.cliente = cliente;
        this.conductor = conductor;
        this.ubicacionOrigen = ubicacionOrigen;
        this.ubicacionDestino = ubicacionDestino;
        this.estado = estado;
        this.vehiculo = vehiculo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }

    public int getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public void setUbicacionOrigen(int ubicacionOrigen) {
        this.ubicacionOrigen = ubicacionOrigen;
    }

    public int getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(int ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
}

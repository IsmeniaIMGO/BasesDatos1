package src.model;

import java.util.Date;

public class ViajeDetalle {
    private int idViaje;
    private Date fecha;
    private String nombreCliente;
    private String nombreConductor;
    private String estado;
    private String vehiculo;
    private int valorPago; // Asumiendo que 'valor' es un int
    private String metodoPago;

    // Constructor vacío
    public ViajeDetalle() {
    }

    // Getters y Setters
    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getValorPago() {
        return valorPago;
    }

    public void setValorPago(int valorPago) {
        this.valorPago = valorPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "ViajeDetalle{" +
               "idViaje=" + idViaje +
               ", fecha=" + fecha +
               ", nombreCliente='" + nombreCliente + '\'' +
               ", nombreConductor='" + nombreConductor + '\'' +
               ", estado='" + estado + '\'' +
               ", vehiculo='" + vehiculo + '\'' +
               ", valorPago=" + valorPago +
               ", metodoPago='" + metodoPago + '\'' +
               '}';
    }
}
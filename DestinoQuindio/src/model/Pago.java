package src.model;

public class Pago {
    private int id;
    private double valor;
    private int metodoPago; // FK a MetodoPago

    public Pago() {}

    public Pago(int id, double valor, int metodoPago) {
        this.id = id;
        this.valor = valor;
        this.metodoPago = metodoPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(int metodoPago) {
        this.metodoPago = metodoPago;
    }
}

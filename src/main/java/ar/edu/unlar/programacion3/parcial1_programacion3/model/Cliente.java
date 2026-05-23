package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public abstract class Cliente {

    protected String idUsuario;
    protected String nombreCompleto;

    public Cliente(String idUsuario, String nombreCompleto) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public abstract double aplicarDescuento(double monto);

}

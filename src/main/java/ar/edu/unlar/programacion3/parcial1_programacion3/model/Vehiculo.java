package ar.edu.unlar.programacion3.parcial1_programacion3.model;

import ar.edu.unlar.programacion3.parcial1_programacion3.state.EstadoEnEspera;
import ar.edu.unlar.programacion3.parcial1_programacion3.state.EstadoVehiculo;

public abstract class Vehiculo implements Comparable<Vehiculo> {

    protected String patente;
    protected int porcentajeBateria;
    protected double tarifaFija;
    private EstadoVehiculo estado;

    public Vehiculo(String patente, int porcentajeBateria, double tarifaFija) {
        this.patente = patente;
        this.porcentajeBateria = porcentajeBateria;
        this.tarifaFija = tarifaFija;
        this.estado = new EstadoEnEspera(this);
    }

    public void iniciarViaje() {
        this.estado.iniciarViaje();
    }

    public void finalizarViaje() {
        this.estado.finalizarViaje();
    }

    public void enviarAReparacion() {
        this.estado.enviarAReparacion();
    }

    @Override
    public int compareTo(Vehiculo otro) {
        return Integer.compare(this.porcentajeBateria, otro.porcentajeBateria);
    }

    public String getPatente() { return patente; }
    public int getPorcentajeBateria() { return porcentajeBateria; }
    public double getTarifaFija() { return tarifaFija; }
    public EstadoVehiculo getEstado() { return estado; }
    public String getNombreEstado() { return estado.getNombre(); }
    public void setEstado(EstadoVehiculo estado) { this.estado = estado; }
    public double calcularTarifa() { return tarifaFija; }
}

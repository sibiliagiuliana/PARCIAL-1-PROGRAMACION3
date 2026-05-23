package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public abstract class Vehiculo {

    protected String patente;
    protected int porcentajeBateria;
    protected double tarifaFija;

    public Vehiculo(String patente, int porcentajeBateria, double tarifaFija) {
        this.patente = patente;
        this.porcentajeBateria = porcentajeBateria;
        this.tarifaFija = tarifaFija;
    }

    public String getPatente() {
        return patente;
    }

    public int getPorcentajeBateria() {
        return porcentajeBateria;
    }

    public double calcularTarifa() {
        return tarifaFija;
    }
}


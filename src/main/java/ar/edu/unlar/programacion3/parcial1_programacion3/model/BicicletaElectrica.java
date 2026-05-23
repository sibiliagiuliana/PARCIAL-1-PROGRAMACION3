package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public class BicicletaElectrica extends Vehiculo {

    private int capacidadCanasto;

    public BicicletaElectrica(String patente, int bateria, double tarifa, int capacidad) {
        super(patente, bateria, tarifa);
        this.capacidadCanasto = capacidad;
    }

    @Override
    public double calcularTarifa() {
        return tarifaFija + capacidadCanasto * 0.1;
    }
}



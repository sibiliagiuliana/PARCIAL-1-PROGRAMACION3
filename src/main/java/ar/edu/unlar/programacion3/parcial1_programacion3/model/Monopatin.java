package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public class Monopatin extends Vehiculo {

    private Boolean amortiguacionReforzada;

    public Monopatin(String patente, int porcentajeBateria, double tarifaFija, Boolean amortiguacion) {
        super(patente, porcentajeBateria, tarifaFija);
        this.amortiguacionReforzada = amortiguacion;
    }

    @Override
    public double calcularTarifa() {
         if (amortiguacionReforzada) {
            return tarifaFija + 50; // extra si tiene amortiguación
        }
        return tarifaFija;
    }

}

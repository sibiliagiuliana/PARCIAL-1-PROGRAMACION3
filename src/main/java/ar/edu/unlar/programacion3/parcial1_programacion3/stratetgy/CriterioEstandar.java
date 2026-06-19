package ar.edu.unlar.programacion3.parcial1_programacion3.stratetgy;
public class CriterioEstandar implements EstrategiaCalculoTarifa {

    @Override
    public double calcularCosto(int minutos, double tarifaFija) {
        // Fórmula base: minutos × tarifa fija del vehículo
        return minutos * tarifaFija;
    }

    @Override
    public String getNombre() { return "ESTANDAR"; }
}

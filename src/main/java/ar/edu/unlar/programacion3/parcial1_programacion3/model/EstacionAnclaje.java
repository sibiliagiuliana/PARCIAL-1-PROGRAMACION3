package ar.edu.unlar.programacion3.parcial1_programacion3.model;

import java.util.List;

public class EstacionAnclaje {

    private String nombreEstacion;
    private List<Vehiculo> vehiculosDisponibles;

    public EstacionAnclaje(String nombreEstacion, List<Vehiculo> vehiculos) {
        this.nombreEstacion = nombreEstacion;
        this.vehiculosDisponibles = vehiculos;
    }

    public String getnombre() {
        return nombreEstacion;
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) {
        for (Vehiculo v : vehiculosDisponibles) {
            if (v.getPatente().equals(patente)) {
                return v;
            }
        }
        return null; // No se encontró el vehículo
    }
      public void eliminarVehiculo(Vehiculo vehiculo) {
        vehiculosDisponibles.remove(vehiculo);
    }
}


package ar.edu.unlar.programacion3.parcial1_programacion3.state;


import ar.edu.unlar.programacion3.parcial1_programacion3.model.Vehiculo;

public class EstadoEnReparacion implements EstadoVehiculo {

    private Vehiculo vehiculo;

    public EstadoEnReparacion(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public void iniciarViaje() {
        // ❌ NUNCA permitido bajo ninguna circunstancia
        throw new RuntimeException("ALARMA: El vehículo está en reparación. No puede iniciar viaje.");
    }

    @Override
    public void finalizarViaje() {
        throw new RuntimeException("El vehículo no está en viaje.");
    }

    @Override
    public void enviarAReparacion() {
        throw new RuntimeException("El vehículo ya está en reparación.");
    }

    @Override
    public String getNombre() { return "EN_REPARACION"; }
}


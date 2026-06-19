package ar.edu.unlar.programacion3.parcial1_programacion3.service;

import ar.edu.unlar.programacion3.parcial1_programacion3.model.*;
import ar.edu.unlar.programacion3.parcial1_programacion3.stratetgy.CriterioEstandar;
import ar.edu.unlar.programacion3.parcial1_programacion3.stratetgy.EstrategiaCalculoTarifa;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlquilerService {

    private final List<EstacionAnclaje> estaciones = new ArrayList<>();
    private final List<Cliente> usuarios = new ArrayList<>();

    private EstrategiaCalculoTarifa estrategiaActiva = new CriterioEstandar();

    private final Map<String, Long> viajesActivos = new HashMap<>();

    public AlquilerService() {
        inicializarDatosEnMemoria();
    }

    public void setEstrategiaCalculoTarifa(EstrategiaCalculoTarifa estrategia) {
        this.estrategiaActiva = estrategia;
    }

    public String getEstrategiaActiva() {
        return estrategiaActiva.getNombre();
    }

    
    public String procesarDesbloqueo(String idUsuario, String patente, String metodoPago) {

        EstacionAnclaje estacionOrigen = null;
        Vehiculo vehiculoEncontrado = null;

        for (int i = 0; i < estaciones.size(); i++) {
            EstacionAnclaje estacion = estaciones.get(i);
            Vehiculo v = estacion.buscarVehiculoPorPatente(patente);
            if (v != null) {
                vehiculoEncontrado = v;
                estacionOrigen = estacion;
                break;
            }
        }

        if (vehiculoEncontrado == null) {
            throw new RuntimeException("Vehículo No Encontrado");
        }
           if (vehiculoEncontrado.getPorcentajeBateria() < 15) {
            throw new RuntimeException("Batería Insuficiente");
        }

        
        vehiculoEncontrado.iniciarViaje();

       
        Cliente clienteActual = buscarUsuarioPorId(idUsuario);
        if (clienteActual == null) {
            throw new RuntimeException("Usuario no registrado en el sistema");
        }

        
        int minutosDesbloqueo = 1;
        double costoCalculado = estrategiaActiva.calcularCosto(
                minutosDesbloqueo,
                vehiculoEncontrado.getTarifaFija()
        );

        double importeFinal = clienteActual.aplicarDescuento(costoCalculado);

        ProcesadorPago procesador = FabricaProcesadorPago.getProcesador(metodoPago);
        procesador.procesarPago(clienteActual, importeFinal);

        viajesActivos.put(patente, System.currentTimeMillis());

        return "Desbloqueo exitoso."
                + " Rodado: " + vehiculoEncontrado.getPatente()
                + " | Estación: " + estacionOrigen.getnombre()
                + " | Estado: " + vehiculoEncontrado.getNombreEstado()
                + " | Criterio: " + estrategiaActiva.getNombre()
                + " | Monto cobrado: $" + String.format("%.2f", importeFinal);
    }

    public String finalizarViaje(String patente) 
        Vehiculo vehiculoEncontrado = null;
        for (int i = 0; i < estaciones.size(); i++) {
            Vehiculo v = estaciones.get(i).buscarVehiculoPorPatente(patente);
            if (v != null) {
                vehiculoEncontrado = v;
                break;
            }
        }

        if (vehiculoEncontrado == null) {
            throw new RuntimeException("Vehículo No Encontrado");
        }

        Long tiempoInicio = viajesActivos.get(patente);
        int minutosTranscurridos = 1; // mínimo 1 minuto
        if (tiempoInicio != null) {
            long milisTranscurridos = System.currentTimeMillis() - tiempoInicio;
            minutosTranscurridos = (int) (milisTranscurridos / 60000);
            if (minutosTranscurridos < 1) {
                minutosTranscurridos = 1;
            }
        }

     
        double costoFinal = estrategiaActiva.calcularCosto(
                minutosTranscurridos,
                vehiculoEncontrado.getTarifaFija()
        );

    
        vehiculoEncontrado.finalizarViaje();

        viajesActivos.remove(patente);

        return "Viaje finalizado."
                + " Rodado: " + vehiculoEncontrado.getPatente()
                + " | Minutos: " + minutosTranscurridos
                + " | Costo final: $" + String.format("%.2f", costoFinal)
                + " | Criterio: " + estrategiaActiva.getNombre()
                + " | Estado actual: " + vehiculoEncontrado.getNombreEstado();
    }


    private Cliente buscarUsuarioPorId(String idUsuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario().equals(idUsuario)) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public List<EstacionAnclaje> getEstaciones() {
        return estaciones;
    }

    private void inicializarDatosEnMemoria() {

        usuarios.add(new UsuarioRegular("USR-001", "Candela Ochoa"));
        usuarios.add(new UsuarioPremium("USR-002", "Juan Pérez", 0.15));

        List<Vehiculo> vehiculosCentro = new ArrayList<>();
        List<Vehiculo> vehiculosNorte = new ArrayList<>();

        Monopatin m1 = new Monopatin("MONO-111", 85, 300.0, true);
        Monopatin m2 = new Monopatin("MONO-222", 12, 300.0, false);
        BicicletaElectrica b1 = new BicicletaElectrica("BICI-333", 90, 450.0, 1200);

        vehiculosCentro.add(m1);
        vehiculosCentro.add(m2);
        vehiculosNorte.add(b1);

        estaciones.add(new EstacionAnclaje("Estación Plaza Principal", vehiculosCentro));
        estaciones.add(new EstacionAnclaje("Estación Campus Universitario", vehiculosNorte));
    }



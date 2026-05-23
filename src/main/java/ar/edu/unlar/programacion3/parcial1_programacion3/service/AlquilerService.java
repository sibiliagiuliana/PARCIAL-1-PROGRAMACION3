package ar.edu.unlar.programacion3.parcial1_programacion3.service;

import ar.edu.unlar.programacion3.parcial1_programacion3.model.*;


import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
    
@Service
    public class AlquilerService {
    
      
        private final List<EstacionAnclaje> estaciones = new ArrayList<>();
        private final List<Cliente> usuarios = new ArrayList<>();
    
        
        public AlquilerService() {
            inicializarDatosEnMemoria();
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
                    break; // Vehículo localizado, rompemos el bucle
                }
            }
    
            if (vehiculoEncontrado == null) {
                throw new RuntimeException("Vehículo No Encontrado");
            }
    
          
            
            if (vehiculoEncontrado.getPorcentajeBateria() < 15) {
                throw new RuntimeException("Batería Insuficiente");
            }
    
            
            Cliente clienteActual = buscarUsuarioPorId(idUsuario);
            if (clienteActual == null) {
                throw new RuntimeException("Usuario no registrado en el sistema");
            }
    
           
            double tarifaBase = vehiculoEncontrado.calcularTarifa();
            
           
            double importeFinal = clienteActual.aplicarDescuento(tarifaBase);
    
            
            ProcesadorPago procesador = FabricaProcesadorPago.getProcesador(metodoPago);
            
            
            procesador.procesarPago(importeFinal);
    
           
            estacionOrigen.eliminarVehiculo(vehiculoEncontrado);
    
          
            return "Desbloqueo exitoso. Rodado: " + vehiculoEncontrado.getPatente() 
                    + " retirado de la estación '" + estacionOrigen.getnombre() 
                    + "'. Monto total cobrado al usuario: $" + String.format("%.2f", importeFinal);
        }
    
        private Cliente buscarUsuarioPorId(String idUsuario) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getIdUsuario().equals(idUsuario)) {
                    return usuarios.get(i);
                }
            }
            return null;
        }
    
        private void inicializarDatosEnMemoria() {
            
            usuarios.add(new UsuarioRegular("USR-001", "Candela Ochoa"));
            usuarios.add(new UsuarioPremium("USR-002", "Juan Pérez", 0.15)); // 15% de descuento fijo
    
            
            List<Vehiculo> vehiculosCentro = new ArrayList<>();
            List<Vehiculo> vehiculosNorte = new ArrayList<>();

            
            Monopatin m1 = new Monopatin("MONO-111", 85, 300.0, true);
            Monopatin m2 = new Monopatin("MONO-222", 12, 300.0, false); // Este va a fallar por batería (< 15%)

            
            BicicletaElectrica b1 = new BicicletaElectrica("BICI-333", 90, 450.0, 1200);

            
            vehiculosCentro.add(m1);
            vehiculosCentro.add(m2);
            vehiculosNorte.add(b1);

           
            EstacionAnclaje estacionCentro = new EstacionAnclaje("Estación Plaza Principal", vehiculosCentro);
            EstacionAnclaje estacionNorte = new EstacionAnclaje("Estación Campus Universitario", vehiculosNorte);
           
            estaciones.add(estacionCentro);
            estaciones.add(estacionNorte);
        }
    }




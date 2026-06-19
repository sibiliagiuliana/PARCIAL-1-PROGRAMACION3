package ar.edu.unlar.programacion3.parcial1_programacion3.controller;

import ar.edu.unlar.programacion3.parcial1_programacion3.comparator.ComparadorTarifaDescendente;
import ar.edu.unlar.programacion3.parcial1_programacion3.dto.VehiculoResponseDTO;
import ar.edu.unlar.programacion3.parcial1_programacion3.model.EstacionAnclaje;
import ar.edu.unlar.programacion3.parcial1_programacion3.model.Vehiculo;
import ar.edu.unlar.programacion3.parcial1_programacion3.service.AlquilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final AlquilerService alquilerService;

    public VehiculoController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // ── GET /api/vehiculos/prioridad-carga ────────────────────────────────
    // Orden natural (Comparable): batería ASCENDENTE
    // Los de menor batería primero → prioridad de carga
    @GetMapping("/prioridad-carga")
    public ResponseEntity<List<VehiculoResponseDTO>> getPorPrioridadCarga() {

        List<Vehiculo> todos = obtenerTodosLosVehiculos();
        Collections.sort(todos); // usa Comparable: batería ASC

        List<VehiculoResponseDTO> respuesta = convertirADTOs(todos);
        return ResponseEntity.ok(respuesta);
    }

    // ── GET /api/vehiculos/tarifa-descendente ─────────────────────────────
    // Orden externo (Comparator): tarifaFija DESCENDENTE
    // Los más caros primero → reporte financiero
    @GetMapping("/tarifa-descendente")
    public ResponseEntity<List<VehiculoResponseDTO>> getPorTarifaDescendente() {

        List<Vehiculo> todos = obtenerTodosLosVehiculos();
        Collections.sort(todos, new ComparadorTarifaDescendente()); // usa Comparator externo

        List<VehiculoResponseDTO> respuesta = convertirADTOs(todos);
        return ResponseEntity.ok(respuesta);
    }

    // ── Métodos auxiliares ─────────────────────────────────────────────────

    // Recolecta todos los vehículos de todas las estaciones
    private List<Vehiculo> obtenerTodosLosVehiculos() {
        List<Vehiculo> todos = new ArrayList<Vehiculo>();
        List<EstacionAnclaje> estaciones = alquilerService.getEstaciones();

        for (int i = 0; i < estaciones.size(); i++) {
            List<Vehiculo> vehiculosEstacion = estaciones.get(i).getTodosLosVehiculos();
            for (int j = 0; j < vehiculosEstacion.size(); j++) {
                todos.add(vehiculosEstacion.get(j));
            }
        }
        return todos;
    }

    // Convierte entidades internas a DTOs (sin exponer datos sensibles)
    private List<VehiculoResponseDTO> convertirADTOs(List<Vehiculo> vehiculos) {
        List<VehiculoResponseDTO> dtos = new ArrayList<VehiculoResponseDTO>();

        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            dtos.add(new VehiculoResponseDTO(
                    v.getPatente(),
                    v.getPorcentajeBateria(),
                    v.getTarifaFija(),
                    v.getNombreEstado()
            ));
        }
        return dtos;
    }
}

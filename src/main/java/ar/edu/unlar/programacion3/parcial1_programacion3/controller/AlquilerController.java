package ar.edu.unlar.programacion3.parcial1_programacion3.controller;

import ar.edu.unlar.programacion3.parcial1_programacion3.model.DesbloqueoRequest;
import ar.edu.unlar.programacion3.parcial1_programacion3.service.AlquilerService;
import ar.edu.unlar.programacion3.parcial1_programacion3.stratetgy.CriterioEstandar;
import ar.edu.unlar.programacion3.parcial1_programacion3.stratetgy.CriterioHoraPico;
    import ar.edu.unlar.programacion3.parcial1_programacion3.stratetgy.CriterioTemporalClimatico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // ── POST /api/alquileres/desbloquear ───────────────────────────────────
    @GetMapping("/desbloquear")
    public ResponseEntity<String> desbloquearVehiculo(@RequestBody DesbloqueoRequest request) {
        try {
            if (request.getIdUsuario() == null
                    || request.getPatente() == null
                    || request.getMetodoPago() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Todos los campos son obligatorios.");
            }

            String resultado = alquilerService.procesarDesbloqueo(
                    request.getIdUsuario(),
                    request.getPatente(),
                    request.getMetodoPago()
            );

            return ResponseEntity.ok(resultado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error de Negocio: " + e.getMessage());
        }
    }

    // ── PUT /api/alquileres/finalizar/{patente} ────────────────────────────
    @PutMapping("/finalizar/{patente}")
    public ResponseEntity<String> finalizarViaje(@PathVariable String patente) {
        try {
            String resultado = alquilerService.finalizarViaje(patente);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error de Negocio: " + e.getMessage());
        }
    }

    // ── PUT /api/alquileres/estrategia/{criterio} ──────────────────────────
    // Cambia el criterio de facturación en tiempo real sin reiniciar la app
    @PutMapping("/estrategia/{criterio}")
    public ResponseEntity<String> cambiarEstrategia(@PathVariable String criterio) {
        if (criterio.equals("ESTANDAR")) {
            alquilerService.setEstrategiaCalculoTarifa(new CriterioEstandar());
        } else if (criterio.equals("HORA_PICO")) {
            alquilerService.setEstrategiaCalculoTarifa(new CriterioHoraPico());
        } else if (criterio.equals("TEMPORAL_CLIMATICO")) {
            alquilerService.setEstrategiaCalculoTarifa(new CriterioTemporalClimatico());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Criterio no válido. Use: ESTANDAR, HORA_PICO o TEMPORAL_CLIMATICO");
        }
        return ResponseEntity.ok("Estrategia cambiada a: " + criterio);
    }
}

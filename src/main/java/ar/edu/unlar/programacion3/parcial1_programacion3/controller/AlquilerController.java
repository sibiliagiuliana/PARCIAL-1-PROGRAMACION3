package ar.edu.unlar.programacion3.parcial1_programacion3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.unlar.programacion3.parcial1_programacion3.model.*;
import ar.edu.unlar.programacion3.parcial1_programacion3.service.*;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    // Inyección de dependencias por constructor (Punto 2: Independencia de Componentes)
    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

   
    @GetMapping("/desbloquear")
    public ResponseEntity<String> desbloquearVehiculo(@RequestBody DesbloqueoRequest request) {
        try {
            /
            if (request.getIdUsuario() == null || request.getPatente() == null || request.getMetodoPago() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Todos los campos del JSON (idUsuario, patente, metodoPago) son obligatorios.");
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

}}

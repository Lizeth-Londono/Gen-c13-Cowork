package com.coworking.cowork.controller;

import com.coworking.cowork.dto.SedeRequestDTO;
import com.coworking.cowork.dto.SedeResponseDTO;
import com.coworking.cowork.service.SedeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Aquí se reciben las solicitudes HTTP relacionadas con las sedes.
@RestController
@RequestMapping("/api/sedes")
public class SedeController {

    // El controlador delega la lógica del CRUD al servicio de sedes.
    private final SedeService sedeService;

    // Spring utiliza este constructor para inyectar el servicio.
    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    // Aquí se consultan todas las sedes con sus espacios resumidos.
    @GetMapping
    public ResponseEntity<List<SedeResponseDTO>> listarTodas() {

        return ResponseEntity.ok(sedeService.listarTodas());
    }

    // Aquí se busca una sede mediante el id recibido en la URL.
    @GetMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> buscarPorId(@PathVariable Long id) {

        return sedeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aquí se recibe la información necesaria para crear una nueva sede.
    @PostMapping
    public ResponseEntity<SedeResponseDTO> crear(
            @RequestBody SedeRequestDTO datos) {

        SedeResponseDTO sedeCreada = sedeService.crear(datos);

        // El estado 201 indica que la sede fue creada correctamente.
        return ResponseEntity.status(HttpStatus.CREATED).body(sedeCreada);
    }

    // Aquí se actualiza la sede que corresponde al id recibido.
    @PutMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody SedeRequestDTO datos) {

        return sedeService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aquí se elimina una sede cuando existe en la base de datos.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (sedeService.eliminarPorId(id)) {

            // El estado 204 confirma la eliminación y no devuelve contenido.
            return ResponseEntity.noContent().build();
        }

        // Si la sede solicitada no existe, la API responde con el estado 404.
        return ResponseEntity.notFound().build();
    }
}
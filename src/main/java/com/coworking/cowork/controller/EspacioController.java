package com.coworking.cowork.controller;

import com.coworking.cowork.dto.EspacioRequestDTO;
import com.coworking.cowork.dto.EspacioResponseDTO;
import com.coworking.cowork.service.EspacioService;
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

// Aquí se reciben las solicitudes HTTP relacionadas con los espacios.
@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    // El controlador delega la lógica del CRUD al servicio de espacios.
    private final EspacioService espacioService;

    // Spring utiliza este constructor para inyectar el servicio.
    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    // Aquí se consultan todos los espacios con su sede y categoría resumidas.
    @GetMapping
    public ResponseEntity<List<EspacioResponseDTO>> listarTodos() {

        return ResponseEntity.ok(espacioService.listarTodos());
    }

    // Aquí se busca un espacio mediante el id recibido en la URL.
    @GetMapping("/{id}")
    public ResponseEntity<EspacioResponseDTO> buscarPorId(@PathVariable Long id) {
        return espacioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aquí se recibe la información necesaria para crear un nuevo espacio.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody EspacioRequestDTO datos) {
        try {
            EspacioResponseDTO espacioCreado = espacioService.crear(datos);

            // El estado 201 indica que el espacio fue creado correctamente.
            return ResponseEntity.status(HttpStatus.CREATED).body(espacioCreado);
        } catch (IllegalArgumentException error) {

            // Un id de sede o categoría inexistente hace inválido el cuerpo enviado.
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    // Aquí se actualiza el espacio que corresponde al id recibido.
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody EspacioRequestDTO datos) {

        try {
            return espacioService.actualizar(id, datos)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException error) {

            // Las relaciones enviadas también deben existir durante la actualización.
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    // Aquí se elimina un espacio cuando existe en la base de datos.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (espacioService.eliminarPorId(id)) {

            // El estado 204 confirma la eliminación y no devuelve contenido.
            return ResponseEntity.noContent().build();
        }

        // Si el espacio solicitado no existe, la API responde con el estado 404.
        return ResponseEntity.notFound().build();
    }
}
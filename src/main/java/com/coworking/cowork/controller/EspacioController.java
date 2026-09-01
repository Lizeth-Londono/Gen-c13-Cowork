package com.coworking.cowork.controller;

import com.coworking.cowork.service.EspacioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coworking.cowork.model.Espacio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

// Aquí se crea el controlador REST que recibirá las solicitudes relacionadas con los espacios.
@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    // Aquí se guarda el servicio que utilizará el controlador.
    private final EspacioService espacioService;

    // Aquí se recibe el servicio mediante inyección por constructor.
    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    // Aquí se atiende la solicitud GET para listar todos los espacios.
    @GetMapping
    public ResponseEntity<List<Espacio>> listarTodos() {

        // Aquí se solicitan al servicio todos los espacios registrados.
        List<Espacio> espacios = espacioService.listarTodos();

        // Aquí se devuelve la lista de espacios con estado HTTP 200.
        return ResponseEntity.ok(espacios);
    }

    // Aquí se atiende la solicitud GET para buscar un espacio por su identificador.
    @GetMapping("/{id}")
    public ResponseEntity<Espacio> buscarPorId(@PathVariable Long id) {

        // Aquí se solicita al servicio buscar el espacio por su identificador.
        Optional<Espacio> espacio = espacioService.buscarPorId(id);

        // Aquí se valida si el espacio fue encontrado.
        if (espacio.isPresent()) {

            // Aquí se devuelve el espacio encontrado con estado HTTP 200.
            return ResponseEntity.ok(espacio.get());
        }

        // Aquí se devuelve estado HTTP 404 cuando el espacio no existe.
        return ResponseEntity.notFound().build();
    }

    // Aquí se atiende la solicitud POST para crear un nuevo espacio.
    @PostMapping
    public ResponseEntity<Espacio> crear(@RequestBody Espacio espacio) {

        // Aquí se solicita al servicio guardar el nuevo espacio.
        Espacio espacioGuardado = espacioService.guardar(espacio);

        // Aquí se devuelve el espacio creado con estado HTTP 201.
        return ResponseEntity.status(HttpStatus.CREATED).body(espacioGuardado);
    }

    // Aquí se atiende la solicitud PUT para actualizar un espacio existente.
    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizar(
            @PathVariable Long id,
            @RequestBody Espacio espacioActualizado) {

        // Aquí se solicita al servicio actualizar el espacio por su identificador.
        Optional<Espacio> espacio = espacioService.actualizar(id, espacioActualizado);

        // Aquí se valida si el espacio fue encontrado y actualizado.
        if (espacio.isPresent()) {

            // Aquí se devuelve el espacio actualizado con estado HTTP 200.
            return ResponseEntity.ok(espacio.get());
        }

        // Aquí se devuelve estado HTTP 404 cuando el espacio no existe.
        return ResponseEntity.notFound().build();
    }

    // Aquí se atiende la solicitud DELETE para eliminar un espacio por su identificador.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        // Aquí se solicita al servicio eliminar el espacio por su identificador.
        boolean eliminado = espacioService.eliminarPorId(id);

        // Aquí se valida si el espacio fue eliminado correctamente.
        if (eliminado) {

            // Aquí se devuelve estado HTTP 204 cuando la eliminación fue exitosa.
            return ResponseEntity.noContent().build();
        }

        // Aquí se devuelve estado HTTP 404 cuando el espacio no existe.
        return ResponseEntity.notFound().build();
    }

}
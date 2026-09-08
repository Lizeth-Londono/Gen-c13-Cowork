package com.coworking.cowork.controller;

import com.coworking.cowork.dto.CategoriaRequestDTO;
import com.coworking.cowork.dto.CategoriaResponseDTO;
import com.coworking.cowork.service.CategoriaService;
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

// Aquí se reciben las solicitudes HTTP relacionadas con las categorías.
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    // El controlador delega la lógica del CRUD al servicio de categorías.
    private final CategoriaService categoriaService;

    // Spring utiliza este constructor para inyectar el servicio.
    public CategoriaController(CategoriaService categoriaService) {

        this.categoriaService = categoriaService;
    }

    // Aquí se consultan todas las categorías registradas.
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {

        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    // Aquí se busca una categoría mediante el id recibido en la URL.
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {

        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aquí se recibe la información para crear una nueva categoría.
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(
            @RequestBody CategoriaRequestDTO datos) {

        CategoriaResponseDTO categoriaCreada = categoriaService.crear(datos);

        // El estado 201 indica que la categoría fue creada correctamente.
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }

    // Aquí se actualiza la categoría que corresponde al id recibido.
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(

            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO datos) {

        return categoriaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aquí se elimina una categoría cuando existe en la base de datos.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (categoriaService.eliminarPorId(id)) {

            // El estado 204 confirma la eliminación y no necesita devolver contenido.
            return ResponseEntity.noContent().build();
        }

        // Si el id no existe, la API responde con el estado 404.
        return ResponseEntity.notFound().build();
    }
}
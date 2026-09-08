package com.coworking.cowork.service;

import com.coworking.cowork.dto.CategoriaRequestDTO;
import com.coworking.cowork.dto.CategoriaResponseDTO;
import com.coworking.cowork.dto.EspacioResumenDTO;
import com.coworking.cowork.model.Categoria;
import com.coworking.cowork.model.Espacio;
import com.coworking.cowork.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Este servicio contiene la lógica del CRUD y la conversión de Categoria a DTO.
@Service
public class CategoriaService {

    // El repositorio permite consultar y modificar las categorías en PostgreSQL.
    private final CategoriaRepository categoriaRepository;

    // Spring utiliza este constructor para inyectar el repositorio.
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // readOnly indica que esta transacción se utiliza únicamente para consultar.
    // La transacción también permite leer los espacios relacionados con carga LAZY.
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {

        return categoriaRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Aquí se busca una categoría por id y se convierte a DTO cuando existe.
    @Transactional(readOnly = true)
    public Optional<CategoriaResponseDTO> buscarPorId(Long id) {

        return categoriaRepository.findById(id)
                .map(this::convertirAResponseDTO);
    }

    // Aquí se crea la entidad utilizando los datos recibidos en el RequestDTO.
    @Transactional
    public CategoriaResponseDTO crear(CategoriaRequestDTO datos) {
        Categoria categoria = new Categoria();
        categoria.setNombre(datos.nombre());

        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        return convertirAResponseDTO(categoriaGuardada);
    }

    // Aquí se actualiza la categoría solamente cuando el id existe.
    @Transactional
    public Optional<CategoriaResponseDTO> actualizar(
            Long id,
            CategoriaRequestDTO datos) {

        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(datos.nombre());

                    Categoria categoriaActualizada =
                            categoriaRepository.save(categoria);

                    return convertirAResponseDTO(categoriaActualizada);
                });
    }

    // Este método informa al controlador si la eliminación pudo realizarse.
    public boolean eliminarPorId(Long id) {
        if (!categoriaRepository.existsById(id)) {

            return false;
        }

        categoriaRepository.deleteById(id);

        return true;
    }

    // Aquí se construye la respuesta sin exponer directamente la entidad JPA.
    private CategoriaResponseDTO convertirAResponseDTO(Categoria categoria) {
        List<EspacioResumenDTO> espacios = categoria.getEspacios().stream()
                .map(this::convertirEspacioAResumenDTO)
                .toList();

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                espacios);
    }

    // El resumen evita devolver nuevamente todas las relaciones de cada espacio.
    private EspacioResumenDTO convertirEspacioAResumenDTO(Espacio espacio) {

        return new EspacioResumenDTO(
                espacio.getId(),
                espacio.getNombre(),
                espacio.getPrecioHora());
    }
}
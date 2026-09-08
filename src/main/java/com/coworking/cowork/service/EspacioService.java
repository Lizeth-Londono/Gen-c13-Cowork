package com.coworking.cowork.service;

import com.coworking.cowork.dto.CategoriaResumenDTO;
import com.coworking.cowork.dto.EspacioRequestDTO;
import com.coworking.cowork.dto.EspacioResponseDTO;
import com.coworking.cowork.dto.SedeResumenDTO;
import com.coworking.cowork.model.Categoria;
import com.coworking.cowork.model.Espacio;
import com.coworking.cowork.model.Sede;
import com.coworking.cowork.repository.CategoriaRepository;
import com.coworking.cowork.repository.EspacioRepository;
import com.coworking.cowork.repository.SedeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Este servicio contiene la lógica del CRUD de Espacio y administra sus relaciones.
@Service
public class EspacioService {

    // Cada repositorio permite consultar la entidad correspondiente en PostgreSQL.
    private final EspacioRepository espacioRepository;
    private final SedeRepository sedeRepository;
    private final CategoriaRepository categoriaRepository;

    // Spring utiliza este constructor para inyectar los tres repositorios.
    public EspacioService(
            EspacioRepository espacioRepository,
            SedeRepository sedeRepository,
            CategoriaRepository categoriaRepository) {

        this.espacioRepository = espacioRepository;
        this.sedeRepository = sedeRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // readOnly indica que esta transacción se utiliza únicamente para consultar.
    // También permite acceder a las relaciones LAZY durante la conversión a DTO.
    @Transactional(readOnly = true)
    public List<EspacioResponseDTO> listarTodos() {

        return espacioRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Aquí se busca un espacio por id y se convierte a DTO cuando existe.
    @Transactional(readOnly = true)
    public Optional<EspacioResponseDTO> buscarPorId(Long id) {

        return espacioRepository.findById(id)
                .map(this::convertirAResponseDTO);
    }

    // Aquí se crea un espacio después de comprobar sus relaciones.
    @Transactional
    public EspacioResponseDTO crear(EspacioRequestDTO datos) {
        Sede sede = buscarSede(datos.sedeId());
        Categoria categoria = buscarCategoria(datos.categoriaId());

        Espacio espacio = new Espacio();
        copiarDatos(datos, espacio, sede, categoria);

        Espacio espacioGuardado = espacioRepository.save(espacio);

        return convertirAResponseDTO(espacioGuardado);
    }

    // Aquí se actualiza un espacio solamente cuando su id existe.
    @Transactional
    public Optional<EspacioResponseDTO> actualizar(
            Long id,
            EspacioRequestDTO datos) {

        Optional<Espacio> espacioEncontrado =
                espacioRepository.findById(id);

        if (espacioEncontrado.isEmpty()) {

            return Optional.empty();
        }

        // Antes de actualizar se comprueba que las nuevas relaciones existan.
        Sede sede = buscarSede(datos.sedeId());
        Categoria categoria = buscarCategoria(datos.categoriaId());
        Espacio espacio = espacioEncontrado.get();

        copiarDatos(datos, espacio, sede, categoria);

        Espacio espacioActualizado = espacioRepository.save(espacio);

        return Optional.of(convertirAResponseDTO(espacioActualizado));
    }

    // Este método informa al controlador si la eliminación pudo realizarse.
    public boolean eliminarPorId(Long id) {
        if (!espacioRepository.existsById(id)) {

            return false;
        }

        espacioRepository.deleteById(id);

        return true;
    }

    // Aquí se obtiene la sede indicada en el RequestDTO.
    // Si el id no existe, el controlador transformará la excepción en un estado 400.
    private Sede buscarSede(Long sedeId) {

        return sedeRepository.findById(sedeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La sede indicada no existe"));
    }

    // Aquí se obtiene la categoría indicada en el RequestDTO.
    // Si el id no existe, el controlador transformará la excepción en un estado 400.
    private Categoria buscarCategoria(Long categoriaId) {

        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La categoría indicada no existe"));
    }

    // Este método reutiliza la asignación de datos al crear y actualizar un espacio.
    private void copiarDatos(
            EspacioRequestDTO datos,
            Espacio espacio,
            Sede sede,
            Categoria categoria) {

        espacio.setNombre(datos.nombre());
        espacio.setCapacidad(datos.capacidad());
        espacio.setPrecioHora(datos.precioHora());
        espacio.setDescripcion(datos.descripcion());
        espacio.setSede(sede);
        espacio.setCategoria(categoria);
    }

    // Aquí se convierte la entidad en la respuesta que será enviada al cliente.
    private EspacioResponseDTO convertirAResponseDTO(Espacio espacio) {
        Sede sede = espacio.getSede();
        Categoria categoria = espacio.getCategoria();

        // Las relaciones se resumen para no exponer las entidades completas
        // ni producir ciclos repetitivos en la respuesta JSON.
        SedeResumenDTO sedeResumen = new SedeResumenDTO(
                sede.getId(),
                sede.getNombre());

        CategoriaResumenDTO categoriaResumen = new CategoriaResumenDTO(
                categoria.getId(),
                categoria.getNombre());

        return new EspacioResponseDTO(
                espacio.getId(),
                espacio.getNombre(),
                espacio.getCapacidad(),
                espacio.getPrecioHora(),
                espacio.getDescripcion(),
                sedeResumen,
                categoriaResumen);
    }
}
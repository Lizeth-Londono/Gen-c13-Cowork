package com.coworking.cowork.service;

import com.coworking.cowork.dto.EspacioResumenDTO;
import com.coworking.cowork.dto.SedeRequestDTO;
import com.coworking.cowork.dto.SedeResponseDTO;
import com.coworking.cowork.model.Espacio;
import com.coworking.cowork.model.Sede;
import com.coworking.cowork.repository.SedeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Este servicio contiene la lógica del CRUD y la conversión de Sede a DTO.
@Service
public class SedeService {

    // El repositorio permite consultar y modificar las sedes en PostgreSQL.
    private final SedeRepository sedeRepository;

    // Spring utiliza este constructor para inyectar el repositorio.
    public SedeService(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }

    // readOnly indica que esta transacción se utiliza únicamente para consultar.
    // También permite leer los espacios relacionados con carga LAZY.
    @Transactional(readOnly = true)
    public List<SedeResponseDTO> listarTodas() {

        return sedeRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Aquí se busca una sede por id y se convierte a DTO cuando existe.
    @Transactional(readOnly = true)
    public Optional<SedeResponseDTO> buscarPorId(Long id) {

        return sedeRepository.findById(id)
                .map(this::convertirAResponseDTO);
    }

    // Aquí se crea la entidad utilizando los datos recibidos en el RequestDTO.
    @Transactional
    public SedeResponseDTO crear(SedeRequestDTO datos) {
        Sede sede = new Sede();
        copiarDatos(datos, sede);

        Sede sedeGuardada = sedeRepository.save(sede);

        return convertirAResponseDTO(sedeGuardada);
    }

    // Aquí se actualiza la sede solamente cuando el id existe.
    @Transactional
    public Optional<SedeResponseDTO> actualizar(
            Long id,
            SedeRequestDTO datos) {

        return sedeRepository.findById(id)
                .map(sede -> {
                    copiarDatos(datos, sede);

                    Sede sedeActualizada = sedeRepository.save(sede);

                    return convertirAResponseDTO(sedeActualizada);
                });
    }

    // Este método informa al controlador si la eliminación pudo realizarse.
    public boolean eliminarPorId(Long id) {
        if (!sedeRepository.existsById(id)) {

            return false;
        }

        sedeRepository.deleteById(id);

        return true;
    }

    // Este método reutiliza la asignación de datos al crear y actualizar una sede.
    private void copiarDatos(SedeRequestDTO datos, Sede sede) {
        sede.setNombre(datos.nombre());
        sede.setDireccion(datos.direccion());
        sede.setCiudad(datos.ciudad());
    }

    // Aquí se construye la respuesta sin exponer directamente la entidad JPA.
    private SedeResponseDTO convertirAResponseDTO(Sede sede) {
        List<EspacioResumenDTO> espacios = sede.getEspacios().stream()
                .map(this::convertirEspacioAResumenDTO)
                .toList();

        return new SedeResponseDTO(
                sede.getId(),
                sede.getNombre(),
                sede.getDireccion(),
                sede.getCiudad(),
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
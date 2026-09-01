package com.coworking.cowork.service;

import com.coworking.cowork.model.Espacio;
import com.coworking.cowork.repository.EspacioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Aquí se crea el servicio que contendrá la lógica de negocio de los espacios.
@Service
public class EspacioService {

    // Aquí se guarda el repositorio que utilizará el servicio.
    private final EspacioRepository espacioRepository;

    // Aquí se recibe el repositorio mediante inyección por constructor.
    public EspacioService(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    // Aquí se solicitan al repositorio todos los espacios guardados.
    public List<Espacio> listarTodos() {
        return espacioRepository.findAll();
    }

    // Aquí se solicita al repositorio buscar un espacio por su identificador.
    public Optional<Espacio> buscarPorId(Long id) {
        return espacioRepository.findById(id);
    }

    // Aquí se solicita al repositorio guardar un nuevo espacio.
    public Espacio guardar(Espacio espacio) {
        return espacioRepository.save(espacio);
    }

    // Aquí se actualiza un espacio existente utilizando JPA.
    public Optional<Espacio> actualizar(Long id, Espacio espacioActualizado) {

        // Aquí se busca en la base de datos el espacio que se quiere actualizar.
        Optional<Espacio> espacioEncontrado = espacioRepository.findById(id);

        // Aquí se valida si el espacio existe.
        if (espacioEncontrado.isPresent()) {

            // Aquí se obtiene la entidad que ya está almacenada en la base de datos.
            Espacio espacioExistente = espacioEncontrado.get();

            // Aquí se actualizan los datos del espacio existente.
            espacioExistente.setNombre(espacioActualizado.getNombre());
            espacioExistente.setCapacidad(espacioActualizado.getCapacidad());
            espacioExistente.setPrecioHora(espacioActualizado.getPrecioHora());
            espacioExistente.setDescripcion(espacioActualizado.getDescripcion());
            espacioExistente.setNombreCategoria(espacioActualizado.getNombreCategoria());
            espacioExistente.setNombreSede(espacioActualizado.getNombreSede());

            // Aquí se guardan los cambios realizados en la base de datos.
            Espacio espacioGuardado = espacioRepository.save(espacioExistente);

            // Aquí se devuelve el espacio actualizado.
            return Optional.of(espacioGuardado);
        }

        // Aquí se indica que no se encontró ningún espacio con ese identificador.
        return Optional.empty();
    }

    // Aquí se elimina un espacio por su identificador.
    public boolean eliminarPorId(Long id) {

        // Aquí se verifica si el espacio existe antes de intentar eliminarlo.
        if (espacioRepository.existsById(id)) {

            // Aquí se elimina el espacio de la base de datos.
            espacioRepository.deleteById(id);

            // Aquí se confirma que la eliminación fue realizada.
            return true;
        }

        // Aquí se indica que no se encontró ningún espacio con ese identificador.
        return false;
    }
}
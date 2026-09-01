package com.coworking.cowork.repository;

import com.coworking.cowork.model.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

// Aquí se crea el repositorio JPA encargado de trabajar con la entidad Espacio.
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
}
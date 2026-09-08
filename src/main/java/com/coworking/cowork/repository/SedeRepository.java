package com.coworking.cowork.repository;

import com.coworking.cowork.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

// Este repositorio permite realizar las operaciones de persistencia de Sede.
// JpaRepository proporciona automáticamente métodos para crear, consultar,
// actualizar y eliminar registros utilizando un identificador de tipo Long.
public interface SedeRepository extends JpaRepository<Sede, Long> {
}
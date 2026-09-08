package com.coworking.cowork.repository;

import com.coworking.cowork.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

// Este repositorio permite realizar las operaciones de persistencia de Categoria.
// JpaRepository proporciona automáticamente métodos para crear, consultar,
// actualizar y eliminar registros utilizando un identificador de tipo Long.
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
package com.coworking.cowork.dto;

// Este DTO presenta únicamente los datos necesarios para identificar una categoría.
// Se utiliza dentro de EspacioResponseDTO para no devolver la entidad completa
// ni producir ciclos repetitivos entre Categoria y Espacio.
public record CategoriaResumenDTO(
        Long id,
        String nombre) {
}
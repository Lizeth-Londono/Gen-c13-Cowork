package com.coworking.cowork.dto;

import java.util.List;

// Este DTO define la información de una categoría que la API devuelve al cliente.
// Incluye sus espacios en versión resumida para evitar una respuesta JSON circular.
public record CategoriaResponseDTO(
        Long id,
        String nombre,
        List<EspacioResumenDTO> espacios) {
}
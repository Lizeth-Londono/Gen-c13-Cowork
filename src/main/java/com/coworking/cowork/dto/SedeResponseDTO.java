package com.coworking.cowork.dto;

import java.util.List;

// Este DTO define la información de una sede que la API devuelve al cliente.
// Incluye sus espacios en versión resumida para mostrar la relación
// sin exponer directamente las entidades JPA ni producir ciclos en el JSON.
public record SedeResponseDTO(
        Long id,
        String nombre,
        String direccion,
        String ciudad,
        List<EspacioResumenDTO> espacios) {
}
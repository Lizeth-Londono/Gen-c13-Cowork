package com.coworking.cowork.dto;

// Este DTO presenta únicamente los datos necesarios para identificar una sede.
// Se utiliza dentro de EspacioResponseDTO para mostrar la relación
// sin devolver todos los espacios asociados a la sede.
public record SedeResumenDTO(
        Long id,
        String nombre) {
}
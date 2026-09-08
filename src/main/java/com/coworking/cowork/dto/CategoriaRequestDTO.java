package com.coworking.cowork.dto;

// Este DTO recibe los datos necesarios para crear o actualizar una categoría.
// No incluye el id porque PostgreSQL lo genera al crear el registro
// y, durante una actualización, el id se recibe directamente desde la URL.
public record CategoriaRequestDTO(
        String nombre) {
}
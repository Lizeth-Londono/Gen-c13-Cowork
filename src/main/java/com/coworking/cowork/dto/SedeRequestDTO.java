package com.coworking.cowork.dto;

// Este DTO recibe los datos necesarios para crear o actualizar una sede.
// No incluye el id porque se genera al crear el registro
// y se recibe desde la URL cuando se realiza una actualización.
public record SedeRequestDTO(
        String nombre,
        String direccion,
        String ciudad) {
}
package com.coworking.cowork.dto;

// Este DTO presenta únicamente los datos principales de un espacio.
// Se utiliza dentro de las respuestas de Sede y Categoria para mostrar
// sus espacios relacionados sin devolver nuevamente todas las relaciones.
public record EspacioResumenDTO(

        Long id,
        String nombre,
        Double precioHora) {
}
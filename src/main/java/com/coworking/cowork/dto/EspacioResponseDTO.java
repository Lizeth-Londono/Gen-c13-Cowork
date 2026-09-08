package com.coworking.cowork.dto;

public record EspacioResponseDTO(
        Long id,
        String nombre,
        Integer capacidad,
        Double precioHora,
        String descripcion,
        SedeResumenDTO sede,
        CategoriaResumenDTO categoria) {
}

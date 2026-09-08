package com.coworking.cowork.dto;

public record EspacioRequestDTO(
        String nombre,
        Integer capacidad,
        Double precioHora,
        String descripcion,
        Long sedeId,
        Long categoriaId) {
}

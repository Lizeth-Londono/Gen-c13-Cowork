package com.coworking.cowork.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// Esta entidad representa cada ubicación física disponible en el coworking.
@Entity
@Table(name = "sedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sede {

    // PostgreSQL genera automáticamente el identificador de cada sede.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El nombre, la dirección y la ciudad son datos obligatorios de la sede.
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    // Una sede puede contener varios espacios.
    // mappedBy indica que Espacio guarda la relación mediante el atributo sede.
    // Las exclusiones de Lombok evitan ciclos al comparar o mostrar las entidades.
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "sede")
    private List<Espacio> espacios = new ArrayList<>();
}
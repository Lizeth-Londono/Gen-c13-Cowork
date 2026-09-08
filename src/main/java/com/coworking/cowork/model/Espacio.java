package com.coworking.cowork.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

// Aquí se indica que la clase representa una entidad que se guardará en la base de datos.
@Entity

// Aquí se define el nombre de la tabla donde se guardarán los espacios.
@Table(name = "espacios")

// Aquí Lombok genera automáticamente getters, setters, equals, hashCode y toString.
@Data

// Aquí Lombok genera el constructor vacío que JPA necesita.
@NoArgsConstructor
public class Espacio {

    // Aquí se define el identificador como clave primaria.
    @Id

    // Aquí se configura la generación automática del identificador en PostgreSQL.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí se guarda el nombre del espacio y se impide que quede nulo.
    @Column(nullable = false)
    private String nombre;

    // Aquí se guarda la capacidad del espacio y se impide que quede nula.
    @Column(nullable = false)
    private Integer capacidad;

    // Aquí se relaciona el atributo precioHora con la columna precio_hora.
    @Column(name = "precio_hora")
    private Double precioHora;

    // Aquí se guarda la descripción del espacio.
    private String descripcion;

    // Aquí se indica que muchos espacios pueden pertenecer a una misma categoría.
    @ManyToOne(fetch = FetchType.LAZY)

    // Aquí se crea la clave foránea categoria_id en la tabla espacios.
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Aquí se indica que muchos espacios pueden pertenecer a una misma sede.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;
}

package com.clinica.sistema.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "inventarios")
public class Inventario {

    @Id
    @Column(name = "idInventario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio")
    private double precio;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}

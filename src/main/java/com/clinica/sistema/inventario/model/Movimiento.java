package com.clinica.sistema.inventario.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @Column(name = "idMovimiento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio")
    private double precio;

    @Column(name = "total")
    private double total;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;



}

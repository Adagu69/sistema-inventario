package com.clinica.sistema.inventario.repository;

import com.clinica.sistema.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    // Método para buscar productos por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombreProducto);

}

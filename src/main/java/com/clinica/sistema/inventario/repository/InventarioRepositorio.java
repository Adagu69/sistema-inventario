package com.clinica.sistema.inventario.repository;

import com.clinica.sistema.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepositorio extends JpaRepository<Inventario, Long> {
    // Método para buscar inventarios por nombre de producto
    List<Inventario> findByProductoNombreContainingIgnoreCase(String nombreProducto);
}
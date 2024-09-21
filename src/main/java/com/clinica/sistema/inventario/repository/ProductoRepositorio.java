package com.clinica.sistema.inventario.repository;

import com.clinica.sistema.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {

}

package com.clinica.sistema.inventario.repository;

import com.clinica.sistema.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepositorio extends JpaRepository<Inventario, Long> {
}
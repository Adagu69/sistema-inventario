package com.clinica.sistema.inventario.repository;

import com.clinica.sistema.inventario.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByTipo(String tipo);
}
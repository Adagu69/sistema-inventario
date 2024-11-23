package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.model.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMovimientoServicio {
    List<Movimiento> listarMovimientos();
    Page<Movimiento> findAll(Pageable pageable);
    Movimiento findOne(Long id);
    Optional<Movimiento> findById(Long id);
    List<Movimiento> obtenerEntradas();
    void guardar(Movimiento movimiento);
    void delete(Long id);
}
package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.model.Movimiento;
import com.clinica.sistema.inventario.repository.MovimientoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServicio implements IMovimientoServicio {

    @Autowired
    private MovimientoRepositorio movimientoRepositorio;

    @Override
    public List<Movimiento> listarMovimientos() {
        return movimientoRepositorio.findAll();
    }


    @Override
    public Page<Movimiento> findAll(Pageable pageable) {
        return movimientoRepositorio.findAll(pageable);
    }

    @Override
    public Movimiento findOne(Long id) {
        return movimientoRepositorio.findById(id).orElse(null);
    }

    @Override
    public Optional<Movimiento> findById(Long id) {
        return movimientoRepositorio.findById(id);
    }

    @Override
    public List<Movimiento> obtenerEntradas() {
        // Filtra los movimientos de tipo 'ENTRADA'
        return movimientoRepositorio.findByTipo("ENTRADA");
    }

    @Override
    @Transactional
    public void guardar(Movimiento movimiento) {
        movimientoRepositorio.save(movimiento);
    }

    @Override
    public void delete(Long id) {
        movimientoRepositorio.deleteById(id);
    }

}
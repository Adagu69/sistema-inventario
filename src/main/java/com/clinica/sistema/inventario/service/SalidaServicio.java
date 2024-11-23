package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.model.Movimiento;
import com.clinica.sistema.inventario.repository.MovimientoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalidaServicio {

    private final MovimientoRepositorio movimientoRepositorio;

    public SalidaServicio(MovimientoRepositorio movimientoRepositorio) {
        this.movimientoRepositorio = movimientoRepositorio;
    }

    public List<Movimiento> obtenerSalidas() {
        return movimientoRepositorio.findByTipo("SALIDA");
    }

    public void guardarSalida(Movimiento movimiento) {
        movimientoRepositorio.save(movimiento);
    }
}
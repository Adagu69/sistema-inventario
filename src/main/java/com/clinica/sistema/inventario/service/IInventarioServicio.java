package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.model.Inventario;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IInventarioServicio {

    List<Inventario> findByProductoNombreContainingIgnoreCase(String nombreProducto);
    Inventario findByProductoIdProducto(Long idProducto);
    Inventario findByIdInventario(Long idInventario); // Changed method name
    Inventario save(Inventario inventario);

}

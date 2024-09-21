package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.model.Producto;
import com.clinica.sistema.inventario.repository.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductoServicio implements IProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = productoRepositorio.findAll();
        return productos;
    }
}

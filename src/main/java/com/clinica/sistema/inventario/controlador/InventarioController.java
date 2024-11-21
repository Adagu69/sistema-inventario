package com.clinica.sistema.inventario.controlador;

import com.clinica.sistema.inventario.model.Inventario;
import com.clinica.sistema.inventario.repository.InventarioRepositorio;
import com.clinica.sistema.inventario.util.reportes.InventarioExporterPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class InventarioController {

    @Autowired
    private InventarioRepositorio inventarioRepositorio;

    // Mostrar inventarios con productos asociados
    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {
        // Obtener todos los inventarios de la base de datos
        List<Inventario> inventarioList = inventarioRepositorio.findAll();
        model.addAttribute("inventarios", inventarioList); // Pasar la lista de inventarios a la vista
        return "inventario"; // Redirigir a la vista inventario.html
    }

    // Generar el reporte PDF
    @GetMapping("/inventario/exportar")
    public void exportarInventario(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=inventario_report.pdf";
        response.setHeader(headerKey, headerValue);

        List<Inventario> inventarios = inventarioRepositorio.findAll();
        InventarioExporterPDF exporter = new InventarioExporterPDF(inventarios);
        exporter.exportar(response);
    }
}
package com.clinica.sistema.inventario.controlador;

import com.clinica.sistema.inventario.model.Movimiento;
import com.clinica.sistema.inventario.service.IMovimientoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/entradas")
public class EntradaControlador {

    private final IMovimientoServicio movimientoServicio;

    public EntradaControlador(IMovimientoServicio movimientoServicio) {
        this.movimientoServicio = movimientoServicio;
    }

    // Cambia de "entrada" a "movimiento" para coincidir con el HTML
    @ModelAttribute("movimiento")
    public Movimiento retornarNuevaEntrada() {
        return new Movimiento();
    }

    @GetMapping
    public String mostrarFormularioDeEntrada(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        List<Movimiento> entradas = movimientoServicio.obtenerEntradas();
        model.addAttribute("movimientos", entradas);
        return "EntradaListar"; // Asegúrate de que coincide con el nombre de tu archivo HTML en templates
    }


    @PostMapping
    public String registrarEntrada(@ModelAttribute("movimiento") Movimiento movimiento, Model model) {
        // Establecer el tipo como 'ENTRADA'
        movimiento.setTipo("entrada");

        // Convertir LocalDateTime a Timestamp y asignarlo a la entrada
        movimiento.setFecha(Timestamp.valueOf(LocalDateTime.now()));

        // Guardar la entrada a través del servicio
        movimientoServicio.guardar(movimiento);

        // Redirigir para actualizar la lista de entradas
        return "redirect:/entradas?exito";
    }
}
package com.clinica.sistema.inventario.controlador;

import com.clinica.sistema.inventario.model.Movimiento;
import com.clinica.sistema.inventario.service.IMovimientoServicio;
import com.clinica.sistema.inventario.service.SalidaServicio;
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
@RequestMapping("/salidas")
public class SalidaControlador {

    private final IMovimientoServicio movimientoServicio;
    private final SalidaServicio salidaServicio;

    public SalidaControlador(IMovimientoServicio movimientoServicio, SalidaServicio salidaServicio) {
        this.movimientoServicio = movimientoServicio;
        this.salidaServicio = salidaServicio;
    }

    @ModelAttribute("movimiento")
    public Movimiento retornarNuevaSalida() {
        return new Movimiento();
    }

    @GetMapping
    public String mostrarFormularioDeSalida(Model model) {
        try {
            // Cargar lista de movimientos de salida
            List<Movimiento> salidas = salidaServicio.obtenerSalidas();
            model.addAttribute("movimientos", salidas);

            // Preparar formulario para nueva salida
            model.addAttribute("movimiento", new Movimiento());
            return "SalidaListar";  // Cambié el nombre de la plantilla a 'SalidaListar'
        } catch (Exception e) {
            e.printStackTrace();  // Verifica el error completo
            model.addAttribute("error", "Ocurrió un error inesperado");
            return "error";  // Página de error personalizada si lo deseas
        }
    }

    @PostMapping
    public String registrarSalida(@ModelAttribute("movimiento") Movimiento movimiento) {
        try {
            // Establecer tipo como 'SALIDA'
            movimiento.setTipo("salida");

            // Asignar fecha actual
            movimiento.setFecha(Timestamp.valueOf(LocalDateTime.now()));

            // Guardar movimiento
            movimientoServicio.guardar(movimiento);

            // Redirigir para mostrar lista actualizada
            return "redirect:/salidas?exito";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";  // Página de error personalizada
        }
    }
}

package com.clinica.sistema.inventario.controlador;

import com.clinica.sistema.inventario.controlador.dto.UsuarioRegistroDTO;
import com.clinica.sistema.inventario.service.IUsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioControlador {

    private IUsuarioServicio usuarioServicio;

    public RegistroUsuarioControlador(IUsuarioServicio usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "registro";
    }

    @PostMapping
    public String registrarCuentaDeUsuario(UsuarioRegistroDTO registroDTO, @RequestParam("role") String role, Model model) {
        boolean isAdmin = "ADMIN".equals(role);
        usuarioServicio.guardar(registroDTO, isAdmin);
        model.addAttribute("exito", "Usuario registrado exitosamente");
        return "redirect:/registro?exito";
    }
}

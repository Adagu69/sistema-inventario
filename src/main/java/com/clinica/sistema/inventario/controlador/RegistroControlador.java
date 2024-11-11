package com.clinica.sistema.inventario.controlador;


import com.clinica.sistema.inventario.service.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RegistroControlador {

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @RequestMapping("/login")
    public String iniciarSesion(){
        return "auth-login";
    }

    @GetMapping("/")
    public String verPaginaDeInicio(Model modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        return "UsuarioListar";
    }

    @RequestMapping("/home")
    public String loginSubmit(){
        return "/pages/landing_page";
    }

}

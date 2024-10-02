package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.controlador.dto.UsuarioRegistroDTO;
import com.clinica.sistema.inventario.model.Usuario;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;

import java.util.List;

public interface IUsuarioServicio {

    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();
}

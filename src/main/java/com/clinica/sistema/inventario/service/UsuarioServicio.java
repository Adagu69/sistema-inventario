package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.controlador.dto.UsuarioRegistroDTO;
import com.clinica.sistema.inventario.model.Rol;
import com.clinica.sistema.inventario.model.Usuario;
import com.clinica.sistema.inventario.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements IUsuarioServicio, UserDetailsService {


    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO, boolean isAdmin) {
        List<Rol> roles = new ArrayList<>();
        if (isAdmin) {
            roles.add(new Rol("ROLE_ADMIN"));
        } else {
            roles.add(new Rol("ROLE_USER"));
        }
        Usuario usuario = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),
                roles);
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(),usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
}


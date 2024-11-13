package com.clinica.sistema.inventario.service;

import com.clinica.sistema.inventario.controlador.dto.UsuarioRegistroDTO;
import com.clinica.sistema.inventario.model.Rol;
import com.clinica.sistema.inventario.model.Usuario;
import com.clinica.sistema.inventario.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    private final  UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio,
                           BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(),
                usuario.getPassword(),
                mapearAutoridadesRoles(usuario.getRoles()));
    }

    @Override
    public List<Usuario> listarUsuarios() {

        return usuarioRepositorio.findAll();
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepositorio.findAll(pageable);
    }

    @Override
    public Usuario findOne(Long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepositorio.findById(id);
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void delete(Long id) {
        usuarioRepositorio.deleteById(id);
    }


    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
    }

//    Método para actualizar un usuario existente
//    public Usuario actualizarUsuario(Long id, Usuario detallesUsuario) {
//        Usuario usuario = usuarioRepositorio.findById(id)
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        // Actualizar los campos según los detalles proporcionados
//        usuario.setNombre(detallesUsuario.getNombre());
//        usuario.setApellido(detallesUsuario.getApellido());
//        usuario.setEmail(detallesUsuario.getEmail());
//        // Puedes actualizar otros campos adicionales aquí
//
//        return usuarioRepositorio.save(usuario);
//    }

}


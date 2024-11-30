package com.clinica.sistema.inventario.controlador.dto;

import java.time.LocalDate;

public class UsuarioRegistroDTO {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private LocalDate fecha;  // Nuevo campo para la fecha


    public UsuarioRegistroDTO(String nombre, String apellido, String email, String password, LocalDate fecha) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.fecha = fecha;
    }

    public UsuarioRegistroDTO() {
    }
    //getters and setters

    public void setPassword(String password) {
        this.password = password;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    // Getters y setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


}

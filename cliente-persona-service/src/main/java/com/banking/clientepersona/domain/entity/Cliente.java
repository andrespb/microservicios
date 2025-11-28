package com.banking.clientepersona.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENTES")
public class Cliente extends Persona {

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @NotNull(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // Constructors
    public Cliente() {
        super();
    }

    public Cliente(String nombre, String genero, Integer edad, String identificacion,
            String direccion, String telefono, String contrasena, Boolean estado) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
        this.contrasena = contrasena;
        this.estado = estado;
    }

    // Getters and Setters
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
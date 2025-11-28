package com.banking.clientepersona.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "PERSONAS")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_seq")
    @SequenceGenerator(name = "persona_seq", sequenceName = "PERSONA_SEQ", allocationSize = 1)
    @Column(name = "persona_id")
    private Long personaId;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "^(M|F|O)$", message = "El género debe ser M, F o O")
    @Column(name = "genero", nullable = false, length = 1)
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @Positive(message = "La edad debe ser positiva")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank(message = "La identificación es obligatoria")
    @Column(name = "identificacion", nullable = false, unique = true, length = 20)
    private String identificacion;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    // Constructors
    public Persona() {
    }

    public Persona(String nombre, String genero, Integer edad, String identificacion, String direccion,
            String telefono) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters and Setters
    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
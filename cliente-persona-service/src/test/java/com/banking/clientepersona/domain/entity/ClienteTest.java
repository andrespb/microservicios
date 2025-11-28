package com.banking.clientepersona.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    void testClienteCreation() {
        // Given
        String nombre = "Juan Pérez";
        String genero = "M";
        Integer edad = 30;
        String identificacion = "12345678";
        String direccion = "Calle Principal 123";
        String telefono = "555-1234";
        String contrasena = "password123";
        Boolean estado = true;

        // When
        cliente = new Cliente(nombre, genero, edad, identificacion, direccion, telefono, contrasena, estado);

        // Then
        assertEquals(nombre, cliente.getNombre());
        assertEquals(genero, cliente.getGenero());
        assertEquals(edad, cliente.getEdad());
        assertEquals(identificacion, cliente.getIdentificacion());
        assertEquals(direccion, cliente.getDireccion());
        assertEquals(telefono, cliente.getTelefono());
        assertEquals(contrasena, cliente.getContrasena());
        assertEquals(estado, cliente.getEstado());
    }

    @Test
    void testClienteIdAssignment() {
        // Given
        Long personaId = 1L;

        // When
        cliente.setPersonaId(personaId);

        // Then
        assertEquals(personaId, cliente.getPersonaId());
    }

    @Test
    void testClienteInheritance() {
        // Given
        String nombre = "María García";
        String identificacion = "87654321";

        // When
        cliente.setNombre(nombre);
        cliente.setIdentificacion(identificacion);

        // Then
        assertTrue(cliente instanceof Persona);
        assertEquals(nombre, cliente.getNombre());
        assertEquals(identificacion, cliente.getIdentificacion());
    }

    @Test
    void testClienteEstadoValidation() {
        // Test estado activo
        cliente.setEstado(true);
        assertTrue(cliente.getEstado());

        // Test estado inactivo
        cliente.setEstado(false);
        assertFalse(cliente.getEstado());
    }

    @Test
    void testClienteDefaultValues() {
        // Verificar que un nuevo cliente tiene valores null por defecto
        assertNull(cliente.getPersonaId());
        assertNull(cliente.getContrasena());
        assertNull(cliente.getEstado());
        assertNull(cliente.getNombre());
    }
}
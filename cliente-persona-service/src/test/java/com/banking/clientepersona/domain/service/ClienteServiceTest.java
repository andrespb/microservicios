package com.banking.clientepersona.domain.service;

import com.banking.clientepersona.application.dto.ClienteRequestDTO;
import com.banking.clientepersona.application.dto.ClienteResponseDTO;
import com.banking.clientepersona.domain.entity.Cliente;
import com.banking.clientepersona.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteRequestDTO clienteRequestDTO;
    private Cliente cliente;
    private ClienteResponseDTO clienteResponseDTO;

    @BeforeEach
    void setUp() {
        clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNombre("Juan Pérez");
        clienteRequestDTO.setGenero("M");
        clienteRequestDTO.setEdad(30);
        clienteRequestDTO.setIdentificacion("12345678");
        clienteRequestDTO.setDireccion("Calle Principal 123");
        clienteRequestDTO.setTelefono("555-1234");
        clienteRequestDTO.setContrasena("password123");
        clienteRequestDTO.setEstado(true);

        cliente = new Cliente();
        cliente.setPersonaId(1L);
        cliente.setNombre("Juan Pérez");
        cliente.setIdentificacion("12345678");
        cliente.setEstado(true);

        clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setClienteId(1L);
        clienteResponseDTO.setNombre("Juan Pérez");
        clienteResponseDTO.setIdentificacion("12345678");
        clienteResponseDTO.setEstado(true);
    }

    @Test
    void testCreateCliente_Success() {
        // Given
        when(clienteRepository.existsByIdentificacion(anyString())).thenReturn(false);
        when(modelMapper.map(clienteRequestDTO, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);

        // When
        ClienteResponseDTO result = clienteService.createCliente(clienteRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(clienteResponseDTO.getClienteId(), result.getClienteId());
        assertEquals(clienteResponseDTO.getNombre(), result.getNombre());
        assertEquals(clienteResponseDTO.getIdentificacion(), result.getIdentificacion());

        verify(clienteRepository).existsByIdentificacion("12345678");
        verify(clienteRepository).save(any(Cliente.class));
        verify(modelMapper).map(clienteRequestDTO, Cliente.class);
        verify(modelMapper).map(cliente, ClienteResponseDTO.class);
    }

    @Test
    void testCreateCliente_IdentificacionAlreadyExists() {
        // Given
        when(clienteRepository.existsByIdentificacion(anyString())).thenReturn(true);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.createCliente(clienteRequestDTO);
        });

        verify(clienteRepository).existsByIdentificacion("12345678");
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testGetClienteById_Found() {
        // Given
        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);

        // When
        Optional<ClienteResponseDTO> result = clienteService.getClienteById(clienteId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(clienteResponseDTO.getClienteId(), result.get().getClienteId());

        verify(clienteRepository).findById(clienteId);
        verify(modelMapper).map(cliente, ClienteResponseDTO.class);
    }

    @Test
    void testGetClienteById_NotFound() {
        // Given
        Long clienteId = 999L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When
        Optional<ClienteResponseDTO> result = clienteService.getClienteById(clienteId);

        // Then
        assertFalse(result.isPresent());

        verify(clienteRepository).findById(clienteId);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void testDeleteCliente_Success() {
        // Given
        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        clienteService.deleteCliente(clienteId);

        // Then
        verify(clienteRepository).findById(clienteId);
        verify(clienteRepository).save(cliente);
        assertFalse(cliente.getEstado()); // Verificar que el estado se cambió a false
    }

    @Test
    void testDeleteCliente_NotFound() {
        // Given
        Long clienteId = 999L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.deleteCliente(clienteId);
        });

        verify(clienteRepository).findById(clienteId);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}
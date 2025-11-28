package com.banking.clientepersona.domain.service;

import com.banking.clientepersona.application.dto.ClienteRequestDTO;
import com.banking.clientepersona.application.dto.ClienteResponseDTO;
import com.banking.clientepersona.domain.entity.Cliente;
import com.banking.clientepersona.infrastructure.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRepository.existsByIdentificacion(clienteRequestDTO.getIdentificacion())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con la identificación: " + clienteRequestDTO.getIdentificacion());
        }

        Cliente cliente = modelMapper.map(clienteRequestDTO, Cliente.class);
        Cliente savedCliente = clienteRepository.save(cliente);
        return modelMapper.map(savedCliente, ClienteResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ClienteResponseDTO> getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class));
    }

    public ClienteResponseDTO updateCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        // Verificar si la nueva identificación ya existe en otro cliente
        if (!existingCliente.getIdentificacion().equals(clienteRequestDTO.getIdentificacion()) &&
                clienteRepository.existsByIdentificacion(clienteRequestDTO.getIdentificacion())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con la identificación: " + clienteRequestDTO.getIdentificacion());
        }

        modelMapper.map(clienteRequestDTO, existingCliente);
        Cliente updatedCliente = clienteRepository.save(existingCliente);
        return modelMapper.map(updatedCliente, ClienteResponseDTO.class);
    }

    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        // Soft delete - cambiar estado a false
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getActiveClientes() {
        List<Cliente> activeClientes = clienteRepository.findActiveClientes();
        return activeClientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
                .collect(Collectors.toList());
    }
}
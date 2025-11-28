package com.banking.clientepersona.presentation.controller;

import com.banking.clientepersona.application.dto.ClienteRequestDTO;
import com.banking.clientepersona.application.dto.ClienteResponseDTO;
import com.banking.clientepersona.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operaciones CRUD para clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Cliente ya existe", content = @Content)
    })
    public ResponseEntity<ClienteResponseDTO> createCliente(
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        try {
            ClienteResponseDTO cliente = clienteService.createCliente(clienteRequestDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista de todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class)))
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        List<ClienteResponseDTO> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Retorna un cliente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ResponseEntity<ClienteResponseDTO> getClienteById(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id) {
        Optional<ClienteResponseDTO> cliente = clienteService.getClienteById(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza completamente un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ResponseEntity<ClienteResponseDTO> updateCliente(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        try {
            ClienteResponseDTO updatedCliente = clienteService.updateCliente(id, clienteRequestDTO);
            return ResponseEntity.ok(updatedCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina (desactiva) un cliente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ResponseEntity<Void> deleteCliente(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener clientes activos", description = "Retorna una lista de todos los clientes activos")
    @ApiResponse(responseCode = "200", description = "Lista de clientes activos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class)))
    public ResponseEntity<List<ClienteResponseDTO>> getActiveClientes() {
        List<ClienteResponseDTO> clientes = clienteService.getActiveClientes();
        return ResponseEntity.ok(clientes);
    }
}
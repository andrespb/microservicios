package com.banking.cuentamovimientos.presentation.controller;

import com.banking.cuentamovimientos.application.dto.MovimientoRequestDTO;
import com.banking.cuentamovimientos.domain.entity.Movimiento;
import com.banking.cuentamovimientos.domain.service.MovimientoService;
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

@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimientos", description = "Operaciones para gestionar movimientos de cuentas")
@CrossOrigin(origins = "*")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Autowired
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo movimiento", description = "Registra un nuevo movimiento en una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o saldo insuficiente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    })
    public ResponseEntity<?> createMovimiento(@Valid @RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        try {
            Movimiento movimiento = movimientoService.createMovimiento(movimientoRequestDTO);
            return new ResponseEntity<>(movimiento, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los movimientos", description = "Retorna una lista de todos los movimientos")
    @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> getAllMovimientos() {
        List<Movimiento> movimientos = movimientoService.getAllMovimientos();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/cuenta/{cuentaId}")
    @Operation(summary = "Obtener movimientos por cuenta", description = "Retorna todos los movimientos de una cuenta específica")
    @ApiResponse(responseCode = "200", description = "Lista de movimientos de la cuenta obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movimiento.class)))
    public ResponseEntity<List<Movimiento>> getMovimientosByCuenta(
            @Parameter(description = "ID de la cuenta", required = true) @PathVariable Long cuentaId) {
        List<Movimiento> movimientos = movimientoService.getMovimientosByCuenta(cuentaId);
        return ResponseEntity.ok(movimientos);
    }
}
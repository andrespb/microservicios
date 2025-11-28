package com.banking.cuentamovimientos.presentation.controller;

import com.banking.cuentamovimientos.application.dto.EstadoCuentaDTO;
import com.banking.cuentamovimientos.domain.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "Operaciones para generar reportes de estado de cuenta")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    @Operation(summary = "Generar estado de cuenta", description = "Genera un reporte de estado de cuenta para un cliente en un rango de fechas específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstadoCuentaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente o cuentas no encontradas", content = @Content)
    })
    public ResponseEntity<?> generarEstadoCuenta(
            @Parameter(description = "ID del cliente", required = true) @RequestParam Long cliente,

            @Parameter(description = "Fecha de inicio del reporte (formato: yyyy-MM-dd)", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,

            @Parameter(description = "Fecha fin del reporte (formato: yyyy-MM-dd)", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {

        try {
            // Convertir fechas a LocalDateTime (inicio del día y fin del día)
            LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
            LocalDateTime fechaFinDateTime = fechaFin.atTime(LocalTime.MAX);

            EstadoCuentaDTO estadoCuenta = reporteService.generarEstadoCuenta(cliente, fechaInicioDateTime,
                    fechaFinDateTime);
            return ResponseEntity.ok(estadoCuenta);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
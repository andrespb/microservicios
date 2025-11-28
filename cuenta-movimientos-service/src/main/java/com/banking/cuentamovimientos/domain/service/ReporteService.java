package com.banking.cuentamovimientos.domain.service;

import com.banking.cuentamovimientos.application.dto.EstadoCuentaDTO;
import com.banking.cuentamovimientos.domain.entity.Cuenta;
import com.banking.cuentamovimientos.domain.entity.Movimiento;
import com.banking.cuentamovimientos.infrastructure.repository.CuentaRepository;
import com.banking.cuentamovimientos.infrastructure.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoService movimientoService;

    @Autowired
    public ReporteService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository,
            MovimientoService movimientoService) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.movimientoService = movimientoService;
    }

    public EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Obtener todas las cuentas del cliente
        List<Cuenta> cuentasCliente = cuentaRepository.findByClienteId(clienteId);

        if (cuentasCliente.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron cuentas para el cliente: " + clienteId);
        }

        // Crear el DTO del estado de cuenta
        EstadoCuentaDTO estadoCuenta = new EstadoCuentaDTO();
        estadoCuenta.setClienteId(clienteId);
        estadoCuenta.setFechaInicio(fechaInicio);
        estadoCuenta.setFechaFin(fechaFin);

        // Procesar cada cuenta del cliente
        List<EstadoCuentaDTO.CuentaReporteDTO> cuentasReporte = cuentasCliente.stream()
                .map(cuenta -> {
                    // Obtener saldo actual de la cuenta
                    BigDecimal saldoActual = movimientoService.getSaldoActual(cuenta.getCuentaId());

                    // Obtener movimientos en el rango de fechas
                    List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
                            cuenta.getCuentaId(), fechaInicio, fechaFin);

                    // Convertir movimientos a DTOs
                    List<EstadoCuentaDTO.MovimientoReporteDTO> movimientosReporte = movimientos.stream()
                            .map(mov -> new EstadoCuentaDTO.MovimientoReporteDTO(
                                    mov.getFecha(),
                                    mov.getTipoMovimiento(),
                                    mov.getValor(),
                                    mov.getSaldo(),
                                    mov.getDescripcion()))
                            .collect(Collectors.toList());

                    return new EstadoCuentaDTO.CuentaReporteDTO(
                            cuenta.getCuentaId(),
                            cuenta.getNumeroCuenta(),
                            cuenta.getTipoCuenta(),
                            saldoActual,
                            movimientosReporte);
                })
                .collect(Collectors.toList());

        estadoCuenta.setCuentas(cuentasReporte);
        return estadoCuenta;
    }
}
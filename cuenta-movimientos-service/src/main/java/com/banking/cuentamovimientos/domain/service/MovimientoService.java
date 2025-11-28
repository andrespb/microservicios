package com.banking.cuentamovimientos.domain.service;

import com.banking.cuentamovimientos.application.dto.MovimientoRequestDTO;
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

@Service
@Transactional
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Movimiento createMovimiento(MovimientoRequestDTO movimientoRequestDTO) {
        Cuenta cuenta = cuentaRepository.findById(movimientoRequestDTO.getCuentaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cuenta no encontrada con id: " + movimientoRequestDTO.getCuentaId()));

        if (!cuenta.getEstado()) {
            throw new IllegalArgumentException("La cuenta no está activa");
        }

        // Calcular el nuevo saldo
        BigDecimal saldoActual = getSaldoActual(cuenta.getCuentaId());
        BigDecimal nuevoSaldo = saldoActual.add(movimientoRequestDTO.getValor());

        // Validar saldo suficiente para débitos
        if (movimientoRequestDTO.getValor().compareTo(BigDecimal.ZERO) < 0 &&
                nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo no disponible");
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(movimientoRequestDTO.getTipoMovimiento());
        movimiento.setValor(movimientoRequestDTO.getValor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuentaId(movimientoRequestDTO.getCuentaId());
        movimiento.setDescripcion(movimientoRequestDTO.getDescripcion());
        movimiento.setFecha(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    @Transactional(readOnly = true)
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Movimiento> getMovimientosByCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
    }

    @Transactional(readOnly = true)
    public BigDecimal getSaldoActual(Long cuentaId) {
        List<Movimiento> movimientos = movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId);

        if (movimientos.isEmpty()) {
            // Si no hay movimientos, retornar el saldo inicial de la cuenta
            Cuenta cuenta = cuentaRepository.findById(cuentaId)
                    .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
            return cuenta.getSaldoInicial();
        }

        return movimientos.get(0).getSaldo(); // El primer movimiento tiene el saldo más actual
    }

    @Transactional(readOnly = true)
    public List<Movimiento> getMovimientosByFechas(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin);
    }
}
package com.banking.cuentamovimientos.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EstadoCuentaDTO {

    private Long clienteId;
    private String nombreCliente;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<CuentaReporteDTO> cuentas;

    // Constructors
    public EstadoCuentaDTO() {
    }

    public EstadoCuentaDTO(Long clienteId, String nombreCliente, LocalDateTime fechaInicio,
            LocalDateTime fechaFin, List<CuentaReporteDTO> cuentas) {
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cuentas = cuentas;
    }

    // Getters and Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<CuentaReporteDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaReporteDTO> cuentas) {
        this.cuentas = cuentas;
    }

    // Inner class para representar la cuenta en el reporte
    public static class CuentaReporteDTO {
        private Long cuentaId;
        private String numeroCuenta;
        private String tipoCuenta;
        private BigDecimal saldoActual;
        private List<MovimientoReporteDTO> movimientos;

        // Constructors
        public CuentaReporteDTO() {
        }

        public CuentaReporteDTO(Long cuentaId, String numeroCuenta, String tipoCuenta,
                BigDecimal saldoActual, List<MovimientoReporteDTO> movimientos) {
            this.cuentaId = cuentaId;
            this.numeroCuenta = numeroCuenta;
            this.tipoCuenta = tipoCuenta;
            this.saldoActual = saldoActual;
            this.movimientos = movimientos;
        }

        // Getters and Setters
        public Long getCuentaId() {
            return cuentaId;
        }

        public void setCuentaId(Long cuentaId) {
            this.cuentaId = cuentaId;
        }

        public String getNumeroCuenta() {
            return numeroCuenta;
        }

        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public String getTipoCuenta() {
            return tipoCuenta;
        }

        public void setTipoCuenta(String tipoCuenta) {
            this.tipoCuenta = tipoCuenta;
        }

        public BigDecimal getSaldoActual() {
            return saldoActual;
        }

        public void setSaldoActual(BigDecimal saldoActual) {
            this.saldoActual = saldoActual;
        }

        public List<MovimientoReporteDTO> getMovimientos() {
            return movimientos;
        }

        public void setMovimientos(List<MovimientoReporteDTO> movimientos) {
            this.movimientos = movimientos;
        }
    }

    // Inner class para representar el movimiento en el reporte
    public static class MovimientoReporteDTO {
        private LocalDateTime fecha;
        private String tipoMovimiento;
        private BigDecimal valor;
        private BigDecimal saldo;
        private String descripcion;

        // Constructors
        public MovimientoReporteDTO() {
        }

        public MovimientoReporteDTO(LocalDateTime fecha, String tipoMovimiento, BigDecimal valor,
                BigDecimal saldo, String descripcion) {
            this.fecha = fecha;
            this.tipoMovimiento = tipoMovimiento;
            this.valor = valor;
            this.saldo = saldo;
            this.descripcion = descripcion;
        }

        // Getters and Setters
        public LocalDateTime getFecha() {
            return fecha;
        }

        public void setFecha(LocalDateTime fecha) {
            this.fecha = fecha;
        }

        public String getTipoMovimiento() {
            return tipoMovimiento;
        }

        public void setTipoMovimiento(String tipoMovimiento) {
            this.tipoMovimiento = tipoMovimiento;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public void setValor(BigDecimal valor) {
            this.valor = valor;
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public void setSaldo(BigDecimal saldo) {
            this.saldo = saldo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
}
package com.banking.cuentamovimientos.infrastructure.repository;

import com.banking.cuentamovimientos.domain.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByClienteId(Long clienteId);

    @Query("SELECT c FROM Cuenta c WHERE c.clienteId = :clienteId AND c.estado = true")
    List<Cuenta> findActiveByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Cuenta c WHERE c.estado = true")
    List<Cuenta> findActiveCuentas();

    boolean existsByNumeroCuenta(String numeroCuenta);
}
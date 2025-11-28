package com.banking.clientepersona.infrastructure.repository;

import com.banking.clientepersona.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);

    @Query("SELECT c FROM Cliente c WHERE c.estado = true")
    java.util.List<Cliente> findActiveClientes();

    @Query("SELECT c FROM Cliente c WHERE c.personaId = :personaId AND c.estado = true")
    Optional<Cliente> findActiveClienteById(@Param("personaId") Long personaId);
}
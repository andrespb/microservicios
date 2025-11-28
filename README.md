# Sistema de Microservicios Bancarios

## Descripción

Sistema bancario basado en microservicios que separa la gestión de clientes/personas y cuentas/movimientos, implementando comunicación asíncrona entre servicios usando RabbitMQ.

## Arquitectura

### Microservicios

1. **Cliente-Persona Service** (Puerto 8080)

   - Gestión de clientes y personas
   - CRUD completo para clientes
   - Herencia JPA (Persona -> Cliente)

2. **Cuenta-Movimientos Service** (Puerto 8081)
   - Gestión de cuentas y movimientos
   - CRU para cuentas y movimientos
   - Generación de reportes de estado de cuenta

### Tecnologías

- **Framework**: Spring Boot 3.2.0
- **Base de Datos**: Oracle Database
- **Mensajería**: RabbitMQ
- **Documentación**: Swagger/OpenAPI 3
- **Testing**: JUnit 5 + Mockito
- **Contenedorización**: Docker + Docker Compose

## Estructura del Proyecto

```
cliente-movimientos/
├── cliente-persona-service/
│   ├── src/main/java/com/banking/clientepersona/
│   │   ├── domain/entity/          # Entidades JPA
│   │   ├── domain/service/         # Servicios de negocio
│   │   ├── application/dto/        # DTOs
│   │   ├── infrastructure/         # Repositorios, configuración
│   │   └── presentation/controller/ # Controladores REST
│   ├── src/test/                   # Pruebas unitarias
│   └── Dockerfile
├── cuenta-movimientos-service/
│   ├── src/main/java/com/banking/cuentamovimientos/
│   │   ├── domain/entity/          # Entidades JPA
│   │   ├── domain/service/         # Servicios de negocio
│   │   ├── application/dto/        # DTOs
│   │   ├── infrastructure/         # Repositorios, mensajería
│   │   └── presentation/controller/ # Controladores REST
│   ├── src/test/                   # Pruebas unitarias
│   └── Dockerfile
├── init-scripts/                   # Scripts de inicialización de BD
└── docker-compose.yml             # Orquestación de contenedores
```

## Endpoints API

### Cliente-Persona Service (http://localhost:8080)

| Método | Endpoint            | Descripción                   |
| ------ | ------------------- | ----------------------------- |
| POST   | `/clientes`         | Crear cliente                 |
| GET    | `/clientes`         | Listar todos los clientes     |
| GET    | `/clientes/{id}`    | Obtener cliente por ID        |
| PUT    | `/clientes/{id}`    | Actualizar cliente            |
| DELETE | `/clientes/{id}`    | Eliminar (desactivar) cliente |
| GET    | `/clientes/activos` | Listar clientes activos       |

### Cuenta-Movimientos Service (http://localhost:8081)

| Método | Endpoint                   | Descripción            |
| ------ | -------------------------- | ---------------------- |
| POST   | `/cuentas`                 | Crear cuenta           |
| GET    | `/cuentas`                 | Listar cuentas         |
| GET    | `/cuentas/{id}`            | Obtener cuenta por ID  |
| PUT    | `/cuentas/{id}`            | Actualizar cuenta      |
| POST   | `/movimientos`             | Registrar movimiento   |
| GET    | `/movimientos`             | Listar movimientos     |
| GET    | `/movimientos/cuenta/{id}` | Movimientos por cuenta |
| GET    | `/reportes`                | Estado de cuenta       |

### Parámetros del Reporte

```
GET /reportes?cliente={clienteId}&fechaInicio={yyyy-MM-dd}&fechaFin={yyyy-MM-dd}
```

## Instalación y Ejecución

### Prerrequisitos

- Docker y Docker Compose
- Java 17+ (para compilación local)
- Maven 3.8+ (para compilación local)

### Ejecución con Docker

1. **Compilar los microservicios**:

   ```bash
   # Cliente-Persona Service
   cd cliente-persona-service
   mvn clean package -DskipTests

   # Cuenta-Movimientos Service
   cd ../cuenta-movimientos-service
   mvn clean package -DskipTests
   ```

2. **Ejecutar con Docker Compose**:

   ```bash
   cd ..
   docker-compose up --build
   ```

3. **Verificar servicios**:
   - Oracle DB: `localhost:1521`
   - RabbitMQ Management: `http://localhost:15672` (guest/guest)
   - Cliente-Persona API: `http://localhost:8080/swagger-ui.html`
   - Cuenta-Movimientos API: `http://localhost:8081/swagger-ui.html`

### Ejecución Local

1. **Configurar Oracle Database**:

   ```bash
   docker run -d --name oracle-db -p 1521:1521 -e ORACLE_PASSWORD=banking_password gvenzl/oracle-xe:21-slim
   ```

2. **Configurar RabbitMQ**:

   ```bash
   docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
   ```

3. **Ejecutar microservicios**:

   ```bash
   # Terminal 1
   cd cliente-persona-service
   mvn spring-boot:run

   # Terminal 2
   cd cuenta-movimientos-service
   mvn spring-boot:run
   ```

## Funcionalidades Implementadas

### F1: CRUD de Entidades

- ✅ CRUD completo para Cliente
- ✅ CRU para Cuenta y Movimiento

### F2: Registro de Movimientos

- ✅ Soporte para valores positivos y negativos
- ✅ Actualización automática de saldo
- ✅ Registro de transacciones

### F3: Validación de Saldo

- ✅ Validación de saldo disponible
- ✅ Mensaje de error: "Saldo no disponible"

### F4: Reportes

- ✅ Reporte de estado de cuenta por cliente y rango de fechas
- ✅ Incluye cuentas con saldos y detalle de movimientos
- ✅ Formato JSON

### F5: Pruebas Unitarias

- ✅ Pruebas para entidad Cliente
- ✅ Pruebas para ClienteService con Mockito

## Documentación API

La documentación interactiva está disponible en:

- Cliente-Persona: `http://localhost:8080/swagger-ui.html`
- Cuenta-Movimientos: `http://localhost:8081/swagger-ui.html`

## Ejemplos de Uso

### Crear Cliente

```json
POST /clientes
{
  "nombre": "Juan Pérez",
  "genero": "M",
  "edad": 30,
  "identificacion": "12345678",
  "direccion": "Calle Principal 123",
  "telefono": "555-1234",
  "contrasena": "password123",
  "estado": true
}
```

### Crear Cuenta

```json
POST /cuentas
{
  "numeroCuenta": "1001234567",
  "tipoCuenta": "AHORROS",
  "saldoInicial": 1000.00,
  "estado": true,
  "clienteId": 1
}
```

### Registrar Movimiento

```json
POST /movimientos
{
  "tipoMovimiento": "DEBITO",
  "valor": -100.00,
  "cuentaId": 1,
  "descripcion": "Retiro ATM"
}
```

### Generar Reporte

```
GET /reportes?cliente=1&fechaInicio=2024-01-01&fechaFin=2024-12-31
```

## Patrones y Buenas Prácticas

- **Clean Architecture**: Separación por capas (Domain, Application, Infrastructure, Presentation)
- **Repository Pattern**: Abstracción de acceso a datos
- **DTO Pattern**: Separación entre entidades y objetos de transferencia
- **Exception Handling**: Manejo global de excepciones
- **Validation**: Validaciones con Bean Validation
- **Documentation**: APIs documentadas con OpenAPI 3
- **Testing**: Pruebas unitarias con JUnit 5 y Mockito

## Consideraciones de Producción

- **Seguridad**: Implementar autenticación JWT y autorización
- **Monitoreo**: Agregar métricas con Micrometer/Prometheus
- **Logging**: Configurar logging centralizado
- **Health Checks**: Endpoints de salud para monitoreo
- **Circuit Breaker**: Implementar patrones de resiliencia
- **Rate Limiting**: Control de tasa de peticiones

# 📮 Colección de Postman - Banking Microservices

Esta carpeta contiene los archivos de Postman para probar los microservicios bancarios.

## 📁 Archivos Incluidos

### 🔧 `Banking-Microservices.postman_collection.json`

Colección completa con todos los endpoints para:

- **Cliente-Persona Service** (Puerto 8080)
- **Cuenta-Movimientos Service** (Puerto 8081)
- **Reportes y Estado de Cuenta**

### 🌍 `Banking-Microservices.postman_environment.json`

Variables de entorno configuradas:

- `cliente_persona_url`: http://localhost:8080
- `cuenta_movimientos_url`: http://localhost:8081
- IDs de prueba para clientes, cuentas y movimientos

## 🚀 Cómo Usar

### 1️⃣ Importar en Postman

```
1. Abrir Postman
2. Click en "Import"
3. Seleccionar ambos archivos JSON
4. Importar la colección y el environment
```

### 2️⃣ Configurar Environment

```
1. En la esquina superior derecha, seleccionar "Banking Microservices Environment"
2. Verificar que las URLs apunten a localhost:8080 y localhost:8081
```

### 3️⃣ Orden de Pruebas Recomendado

#### **Cliente-Persona Service:**

1. ✅ Health Check
2. 🆕 Crear Cliente (Juan)
3. 🆕 Crear Cliente (María)
4. 📋 Obtener Todos los Clientes
5. 🔍 Obtener Cliente por ID
6. ✏️ Actualizar Cliente
7. ❌ Eliminar Cliente

#### **Cuenta-Movimientos Service:**

1. ✅ Health Check
2. 🏦 Crear Cuenta
3. 📋 Obtener Todas las Cuentas
4. 💰 Crear Movimiento - Depósito
5. 💸 Crear Movimiento - Retiro
6. 📊 Obtener Todos los Movimientos
7. 📈 Reporte Estado de Cuenta

## 🔍 Endpoints Disponibles

### **Cliente-Persona Service** (8080)

- `GET /actuator/health` - Health Check
- `POST /api/clientes` - Crear Cliente
- `GET /api/clientes` - Obtener Todos
- `GET /api/clientes/{id}` - Obtener por ID
- `PUT /api/clientes/{id}` - Actualizar
- `DELETE /api/clientes/{id}` - Eliminar

### **Cuenta-Movimientos Service** (8081)

- `GET /actuator/health` - Health Check
- `POST /api/cuentas` - Crear Cuenta
- `GET /api/cuentas` - Obtener Todas
- `POST /api/movimientos` - Crear Movimiento
- `GET /api/movimientos` - Obtener Todos
- `GET /api/reportes/estado-cuenta` - Reporte

## 📝 Datos de Prueba

### Cliente Ejemplo:

```json
{
  "nombre": "Juan Pérez García",
  "genero": "M",
  "edad": 35,
  "identificacion": "1234567890",
  "direccion": "Av. Principal 123, Ciudad",
  "telefono": "+1-555-0123",
  "contrasena": "segura123",
  "estado": true
}
```

### Cuenta Ejemplo:

```json
{
  "numeroCuenta": "1001234567",
  "tipoCuenta": "AHORROS",
  "saldoInicial": 1000.0,
  "estado": true,
  "clienteId": 1
}
```

### Movimiento Ejemplo:

```json
{
  "tipoMovimiento": "DEPOSITO",
  "valor": 500.0,
  "cuentaId": 1
}
```

## ⚠️ Prerequisitos

1. **Servicios Ejecutándose:**

   - Cliente-Persona Service en puerto 8080
   - Cuenta-Movimientos Service en puerto 8081
   - Oracle Database en puerto 1521
   - RabbitMQ en puerto 5672

2. **Verificar Health Checks:**
   ```bash
   curl http://localhost:8080/actuator/health
   curl http://localhost:8081/actuator/health
   ```

## 🐛 Troubleshooting

- **Error de conexión**: Verificar que los servicios estén ejecutándose
- **Error 500**: Revisar logs de Docker Compose
- **Variables no encontradas**: Asegurar que el environment esté seleccionado

---

¡Listos para probar los microservicios bancarios! 🚀💳

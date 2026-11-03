# Taller Backend Hotel

API REST para clientes, disponibilidad de habitaciones, check-in, check-out y facturas.
Implementada con Java, Spring Boot, Spring Data JPA, Hibernate, Lombok,
Bean Validation, SQL Server y Swagger/OpenAPI.

## Requisitos

- Windows de 64 bits y JDK 17 o superior.
- SQL Server local, con TCP habilitado en el puerto 1433.
- Autenticacion de Windows y permisos para crear la base `hotel`.
- `sqlcmd` para ejecutar los scripts desde la terminal (tambien pueden ejecutarse en SSMS).

El proyecto usa Maven Wrapper; no requiere instalar Maven globalmente.
La DLL `.local/mssql-jdbc_auth-13.4.0.x64.dll` permite la autenticacion de Windows
con el controlador JDBC 13.4.0. Maven configura su ruta para las pruebas y el arranque.
Distribucion oficial: https://repo.maven.apache.org/maven2/com/microsoft/sqlserver/mssql-jdbc_auth/13.4.0.x64/

## Preparacion de la base de datos

Desde la raiz del proyecto, ejecutar cada comando en orden y detenerse si hay errores:

```powershell
sqlcmd -S localhost -E -C -b -i sql/01_crear_base.sql
sqlcmd -S localhost -E -C -b -i sql/02_cargar_habitaciones.sql
sqlcmd -S localhost -E -C -b -i sql/03_datos_prueba.sql
```

Los scripts estan pensados para una instalacion nueva y se ejecutan una sola vez.
No repetirlos sobre una base ya preparada. El primero crea las tablas, relaciones,
indices y la proteccion contra modificaciones y eliminaciones de facturas.
El segundo carga las 15 habitaciones. El tercero registra tres clientes y tres
hospedajes activos, en las habitaciones 101, 201 y 301.

## Ejecucion

La conexion esta en `src/main/resources/application.yml` y utiliza la cuenta de
Windows que ejecuta Java. Si el servidor es diferente, ajustar `HOTEL_DB_URL`
con una URL JDBC apropiada antes del arranque.

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui/index.html

OpenAPI: http://localhost:8080/v3/api-docs

Para ejecutar directamente desde IntelliJ, agregar a las opciones de VM
`-Djava.library.path="RUTA_ABSOLUTA_DEL_PROYECTO/.local"`.

## Endpoints

| Metodo | Ruta | Operacion |
| --- | --- | --- |
| POST | /api/clientes | Crear cliente |
| GET | /api/clientes | Listar clientes |
| GET | /api/clientes/{id} | Consultar cliente por ID |
| GET | /api/clientes/documento/{identificacion} | Consultar por identificacion |
| GET | /api/habitaciones | Listar habitaciones |
| GET | /api/habitaciones/{numero} | Consultar habitacion |
| GET | /api/habitaciones/disponibles/{tipo} | Consultar una habitacion disponible del tipo solicitado |
| POST | /api/checkin | Registrar ingreso |
| POST | /api/checkout | Registrar salida y generar factura |
| GET | /api/facturas | Listar facturas |
| GET | /api/facturas/{id} | Consultar factura |

Tipos de habitacion: `ESTANDAR`, `PREMIER`, `SUITE`.
El check-in permite ingresos antes de las 16:00 y registra la hora real.
El check-out factura al menos una noche; en dias posteriores al ingreso,
una salida despues de las 13:00 suma una noche adicional.

## Prueba manual del flujo

1. Crear un cliente mediante `POST /api/clientes` y copiar el ID devuelto:

```json
{
  "identificacion": "DEMO001",
  "nombreCompleto": "Carlos Ruiz",
  "correoElectronico": "carlos@example.com",
  "telefono": "3004444444"
}
```

2. Enviar a `POST /api/checkin` el ID real y el tipo de habitacion:

```json
{"clienteId": 4, "tipoHabitacion": "ESTANDAR"}
```

3. Repetir el check-in debe producir HTTP 409.
4. Enviar el mismo ID a `POST /api/checkout`:

```json
{"clienteId": 4}
```

5. Comprobar la factura y que la habitacion asignada quede disponible.
6. Repetir el check-out debe producir HTTP 404.

## Estado de verificacion

- Compilacion y prueba de carga del contexto con SQL Server: correctas.
- Consulta HTTP de habitaciones: 15 registros, con 101, 201 y 301 ocupadas tras la carga inicial.
- Swagger y OpenAPI: accesibles.
- Calculo de noches: verificado con los tres ejemplos de la guia y la salida exacta a las 13:00.
- El flujo completo de escritura por HTTP y las pruebas de concurrencia estan pendientes de verificar.

El proyecto incluye los scripts SQL y el historial de desarrollo en Git.

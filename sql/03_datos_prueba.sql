SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
SET ANSI_PADDING ON;
SET ANSI_WARNINGS ON;
SET ARITHABORT ON;
SET CONCAT_NULL_YIELDS_NULL ON;
SET NUMERIC_ROUNDABORT OFF;
GO

USE hotel;
GO

SET XACT_ABORT ON;
BEGIN TRANSACTION;

DECLARE @ahora DATETIME2(6) = SYSDATETIME();

INSERT INTO cliente
(identificacion, nombre_completo, correo_electronico,
 telefono, fecha_creacion)
VALUES
('PRUEBA001', 'Ana Perez', 'ana@example.com', '3001111111', @ahora),
('PRUEBA002', 'Luis Gomez', 'luis@example.com', '3002222222', @ahora),
('PRUEBA003', 'Maria Lopez', 'maria@example.com', '3003333333', @ahora);

-- Un hospedaje por cada tipo de habitacion.
INSERT INTO hospedaje
(cliente_id, habitacion_id, fecha_hora_ingreso,
 fecha_hora_salida, activo, fecha_creacion)
SELECT
    c.id,
    h.id,
    @ahora,
    NULL,
    1,
    @ahora
FROM (
    VALUES
        ('PRUEBA001', 101),
        ('PRUEBA002', 201),
        ('PRUEBA003', 301)
) AS datos(identificacion, numero_habitacion)
INNER JOIN cliente c
    ON c.identificacion = datos.identificacion
INNER JOIN habitacion h
    ON h.numero_habitacion = datos.numero_habitacion;

UPDATE habitacion
SET estado = 'OCUPADA'
WHERE numero_habitacion IN (101, 201, 301);

COMMIT TRANSACTION;
GO

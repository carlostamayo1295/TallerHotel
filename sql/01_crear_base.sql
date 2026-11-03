SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
SET ANSI_PADDING ON;
SET ANSI_WARNINGS ON;
SET ARITHABORT ON;
SET CONCAT_NULL_YIELDS_NULL ON;
SET NUMERIC_ROUNDABORT OFF;
GO

USE master;
GO

IF DB_ID(N'hotel') IS NULL
    EXEC(N'CREATE DATABASE hotel');
GO

USE hotel;
GO

CREATE TABLE cliente (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    identificacion VARCHAR(255) NOT NULL UNIQUE,
    nombre_completo VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME2(6) NOT NULL
);
GO

CREATE TABLE habitacion (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    numero_habitacion INT NOT NULL UNIQUE,
    piso INT NOT NULL,
    tipo_habitacion VARCHAR(255) NOT NULL,
    valor_noche DECIMAL(12,2) NOT NULL,
    estado VARCHAR(255) NOT NULL,

    CONSTRAINT ck_habitacion_tipo
        CHECK (tipo_habitacion IN ('ESTANDAR', 'PREMIER', 'SUITE')),

    CONSTRAINT ck_habitacion_estado
        CHECK (estado IN ('DISPONIBLE', 'OCUPADA'))
);
GO

CREATE TABLE hospedaje (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    habitacion_id BIGINT NOT NULL,
    fecha_hora_ingreso DATETIME2(6) NOT NULL,
    fecha_hora_salida DATETIME2(6),
    activo BIT NOT NULL,
    fecha_creacion DATETIME2(6) NOT NULL,

    CONSTRAINT fk_hospedaje_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente(id),

    CONSTRAINT fk_hospedaje_habitacion
        FOREIGN KEY (habitacion_id) REFERENCES habitacion(id)
);
GO

-- Un cliente solo puede tener un hospedaje activo.
CREATE UNIQUE INDEX ux_hospedaje_cliente_activo
    ON hospedaje(cliente_id)
    WHERE activo = 1;
GO

-- Una habitacion solo puede tener un hospedaje activo.
CREATE UNIQUE INDEX ux_hospedaje_habitacion_activa
    ON hospedaje(habitacion_id)
    WHERE activo = 1;
GO

CREATE TABLE factura (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    hospedaje_id BIGINT NOT NULL UNIQUE,
    fecha_generacion DATETIME2(6) NOT NULL,
    cantidad_noches INT NOT NULL,
    valor_noche DECIMAL(12,2) NOT NULL,
    valor_total DECIMAL(15,2) NOT NULL,

    CONSTRAINT fk_factura_hospedaje
        FOREIGN KEY (hospedaje_id) REFERENCES hospedaje(id),

    CONSTRAINT ck_factura_noches
        CHECK (cantidad_noches >= 1)
);
GO

-- Las facturas no pueden modificarse ni eliminarse.
CREATE TRIGGER tr_factura_inmutable
ON factura
INSTEAD OF UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    THROW 50001, 'Las facturas no pueden modificarse ni eliminarse.', 1;
END;
GO

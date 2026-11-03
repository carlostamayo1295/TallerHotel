package co.edu.cesde.hotel.dto;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String identificacion,
        String nombreCompleto,
        String correoElectronico,
        String telefono,
        LocalDateTime fechaCreacion
) {}

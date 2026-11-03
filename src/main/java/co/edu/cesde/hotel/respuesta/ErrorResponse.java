package co.edu.cesde.hotel.respuesta;

import java.time.LocalDateTime;

public record ErrorResponse(
        int codigoHttp,
        String error,
        String mensaje,
        LocalDateTime fechaHora
) {}

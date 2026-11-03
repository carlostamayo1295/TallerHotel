package co.edu.cesde.hotel.dto;

import co.edu.cesde.hotel.entidad.TipoHabitacion;
import java.time.LocalDateTime;

public record CheckInResponse(
        Long hospedajeId,
        Long clienteId,
        String cliente,
        Integer numeroHabitacion,
        TipoHabitacion tipoHabitacion,
        LocalDateTime fechaHoraIngreso,
        String mensaje
) {}

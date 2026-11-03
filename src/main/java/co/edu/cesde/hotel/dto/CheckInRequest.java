package co.edu.cesde.hotel.dto;

import co.edu.cesde.hotel.entidad.TipoHabitacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckInRequest(
        @NotNull(message = "El cliente es obligatorio")
        @Positive(message = "El ID debe ser positivo")
        Long clienteId,

        @NotNull(message = "El tipo de habitación es obligatorio")
        TipoHabitacion tipoHabitacion
) {}

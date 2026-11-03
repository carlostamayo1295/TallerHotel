package co.edu.cesde.hotel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckOutRequest(
        @NotNull(message = "El cliente es obligatorio")
        @Positive(message = "El ID debe ser positivo")
        Long clienteId
) {}

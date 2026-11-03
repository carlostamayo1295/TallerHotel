package co.edu.cesde.hotel.dto;

import co.edu.cesde.hotel.entidad.TipoHabitacion;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FacturaResponse(
        Long facturaId,
        Long clienteId,
        String cliente,
        Integer numeroHabitacion,
        TipoHabitacion tipoHabitacion,
        LocalDateTime fechaHoraIngreso,
        LocalDateTime fechaHoraSalida,
        Integer cantidadNoches,
        BigDecimal valorNoche,
        BigDecimal valorTotal,
        LocalDateTime fechaGeneracion
) {}

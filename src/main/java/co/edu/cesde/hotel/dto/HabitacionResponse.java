package co.edu.cesde.hotel.dto;

import co.edu.cesde.hotel.entidad.EstadoHabitacion;
import co.edu.cesde.hotel.entidad.TipoHabitacion;
import java.math.BigDecimal;

public record HabitacionResponse(
        Long id,
        Integer numeroHabitacion,
        Integer piso,
        TipoHabitacion tipoHabitacion,
        BigDecimal valorNoche,
        EstadoHabitacion estado
) {}

package co.edu.cesde.hotel.servicio;

import co.edu.cesde.hotel.dto.HabitacionResponse;
import co.edu.cesde.hotel.entidad.TipoHabitacion;
import java.util.List;

public interface ServicioHabitacion {
    List<HabitacionResponse> consultarTodas();
    HabitacionResponse consultarPorNumero(Integer numero);
    HabitacionResponse buscarDisponiblePorTipo(TipoHabitacion tipo);
}

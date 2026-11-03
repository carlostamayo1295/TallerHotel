package co.edu.cesde.hotel.repositorio;

import co.edu.cesde.hotel.entidad.EstadoHabitacion;
import co.edu.cesde.hotel.entidad.Habitacion;
import co.edu.cesde.hotel.entidad.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioHabitacion extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumeroHabitacion(Integer numeroHabitacion);

    List<Habitacion> findByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
            TipoHabitacion tipoHabitacion,
            EstadoHabitacion estado);
}

package co.edu.cesde.hotel.servicio.impl;

import co.edu.cesde.hotel.dto.HabitacionResponse;
import co.edu.cesde.hotel.entidad.EstadoHabitacion;
import co.edu.cesde.hotel.entidad.Habitacion;
import co.edu.cesde.hotel.entidad.TipoHabitacion;
import co.edu.cesde.hotel.excepcion.HabitacionNoDisponibleException;
import co.edu.cesde.hotel.excepcion.HabitacionNoEncontradaException;
import co.edu.cesde.hotel.repositorio.RepositorioHabitacion;
import co.edu.cesde.hotel.servicio.ServicioHabitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioHabitacionImpl implements ServicioHabitacion {

    private final RepositorioHabitacion repositorioHabitacion;

    @Override
    public List<HabitacionResponse> consultarTodas() {
        return repositorioHabitacion.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    @Override
    public HabitacionResponse consultarPorNumero(Integer numero) {
        Habitacion habitacion = repositorioHabitacion
                .findByNumeroHabitacion(numero)
                .orElseThrow(() -> new HabitacionNoEncontradaException(
                        "No existe la habitación " + numero));

        return convertir(habitacion);
    }

    @Override
    public HabitacionResponse buscarDisponiblePorTipo(TipoHabitacion tipo) {
        Habitacion habitacion = repositorioHabitacion
                .findByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
                        tipo, EstadoHabitacion.DISPONIBLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new HabitacionNoDisponibleException(
                        "No hay habitaciones disponibles de tipo " + tipo));

        return convertir(habitacion);
    }

    private HabitacionResponse convertir(Habitacion habitacion) {
        return new HabitacionResponse(
                habitacion.getId(),
                habitacion.getNumeroHabitacion(),
                habitacion.getPiso(),
                habitacion.getTipoHabitacion(),
                habitacion.getValorNoche(),
                habitacion.getEstado()
        );
    }
}

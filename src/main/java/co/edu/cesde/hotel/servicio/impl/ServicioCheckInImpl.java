package co.edu.cesde.hotel.servicio.impl;

import co.edu.cesde.hotel.dto.CheckInRequest;
import co.edu.cesde.hotel.dto.CheckInResponse;
import co.edu.cesde.hotel.entidad.Cliente;
import co.edu.cesde.hotel.entidad.EstadoHabitacion;
import co.edu.cesde.hotel.entidad.Habitacion;
import co.edu.cesde.hotel.entidad.Hospedaje;
import co.edu.cesde.hotel.excepcion.ClienteConHospedajeActivoException;
import co.edu.cesde.hotel.excepcion.ClienteNoEncontradoException;
import co.edu.cesde.hotel.excepcion.HabitacionNoDisponibleException;
import co.edu.cesde.hotel.repositorio.RepositorioCliente;
import co.edu.cesde.hotel.repositorio.RepositorioHabitacion;
import co.edu.cesde.hotel.repositorio.RepositorioHospedaje;
import co.edu.cesde.hotel.servicio.ServicioCheckIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServicioCheckInImpl implements ServicioCheckIn {

    private final RepositorioCliente repositorioCliente;
    private final RepositorioHabitacion repositorioHabitacion;
    private final RepositorioHospedaje repositorioHospedaje;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CheckInResponse realizarCheckIn(CheckInRequest request) {
        Cliente cliente = repositorioCliente.findById(request.clienteId())
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe el cliente con ID " + request.clienteId()));

        if (repositorioHospedaje.existsByClienteIdAndActivoTrue(cliente.getId())) {
            throw new ClienteConHospedajeActivoException(
                    "El cliente ya tiene un hospedaje activo");
        }

        Habitacion habitacion = repositorioHabitacion
                .findByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
                        request.tipoHabitacion(), EstadoHabitacion.DISPONIBLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new HabitacionNoDisponibleException(
                        "No existen habitaciones disponibles de tipo "
                                + request.tipoHabitacion()));

        Hospedaje hospedaje = new Hospedaje();
        hospedaje.setCliente(cliente);
        hospedaje.setHabitacion(habitacion);
        hospedaje.setFechaHoraIngreso(LocalDateTime.now());
        hospedaje.setActivo(true);

        habitacion.setEstado(EstadoHabitacion.OCUPADA);
        repositorioHabitacion.save(habitacion);

        Hospedaje guardado = repositorioHospedaje.save(hospedaje);

        return new CheckInResponse(
                guardado.getId(),
                cliente.getId(),
                cliente.getNombreCompleto(),
                habitacion.getNumeroHabitacion(),
                habitacion.getTipoHabitacion(),
                guardado.getFechaHoraIngreso(),
                "Check-in realizado correctamente"
        );
    }
}

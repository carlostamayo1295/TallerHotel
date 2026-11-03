package co.edu.cesde.hotel.servicio.impl;

import co.edu.cesde.hotel.dto.CheckOutRequest;
import co.edu.cesde.hotel.dto.FacturaResponse;
import co.edu.cesde.hotel.entidad.EstadoHabitacion;
import co.edu.cesde.hotel.entidad.Factura;
import co.edu.cesde.hotel.entidad.Hospedaje;
import co.edu.cesde.hotel.excepcion.CheckOutInvalidoException;
import co.edu.cesde.hotel.excepcion.HospedajeActivoNoEncontradoException;
import co.edu.cesde.hotel.repositorio.RepositorioFactura;
import co.edu.cesde.hotel.repositorio.RepositorioHabitacion;
import co.edu.cesde.hotel.repositorio.RepositorioHospedaje;
import co.edu.cesde.hotel.servicio.ServicioCheckOut;
import co.edu.cesde.hotel.servicio.ServicioFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ServicioCheckOutImpl implements ServicioCheckOut {

    private final RepositorioHospedaje repositorioHospedaje;
    private final RepositorioHabitacion repositorioHabitacion;
    private final RepositorioFactura repositorioFactura;
    private final ServicioFactura servicioFactura;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FacturaResponse realizarCheckOut(CheckOutRequest request) {
        Hospedaje hospedaje = repositorioHospedaje
                .findByClienteIdAndActivoTrue(request.clienteId())
                .orElseThrow(() -> new HospedajeActivoNoEncontradoException(
                        "El cliente no tiene un hospedaje activo"));

        LocalDateTime salida = LocalDateTime.now();
        LocalDateTime ingreso = hospedaje.getFechaHoraIngreso();

        if (salida.isBefore(ingreso)) {
            throw new CheckOutInvalidoException(
                    "La salida no puede ser anterior al ingreso");
        }

        int noches = calcularNoches(ingreso, salida);

        hospedaje.setFechaHoraSalida(salida);
        hospedaje.setActivo(false);
        hospedaje.getHabitacion().setEstado(EstadoHabitacion.DISPONIBLE);

        repositorioHospedaje.save(hospedaje);
        repositorioHabitacion.save(hospedaje.getHabitacion());

        Factura factura = new Factura(
                hospedaje,
                noches,
                hospedaje.getHabitacion().getValorNoche()
        );

        Factura guardada = repositorioFactura.save(factura);

        return servicioFactura.consultarPorId(guardada.getId());
    }

    private int calcularNoches(LocalDateTime ingreso, LocalDateTime salida) {
        long noches = ChronoUnit.DAYS.between(
                ingreso.toLocalDate(),
                salida.toLocalDate()
        );

        if (noches > 0 && salida.toLocalTime().isAfter(LocalTime.of(13, 0))) {
            noches++;
        }

        return Math.toIntExact(Math.max(1, noches));
    }
}

package co.edu.cesde.hotel.servicio.impl;

import co.edu.cesde.hotel.dto.FacturaResponse;
import co.edu.cesde.hotel.entidad.Factura;
import co.edu.cesde.hotel.entidad.Hospedaje;
import co.edu.cesde.hotel.excepcion.FacturaNoEncontradaException;
import co.edu.cesde.hotel.repositorio.RepositorioFactura;
import co.edu.cesde.hotel.servicio.ServicioFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioFacturaImpl implements ServicioFactura {

    private final RepositorioFactura repositorioFactura;

    @Override
    public FacturaResponse consultarPorId(Long id) {
        Factura factura = repositorioFactura.findById(id)
                .orElseThrow(() -> new FacturaNoEncontradaException(
                        "No existe la factura con ID " + id));

        return convertir(factura);
    }

    @Override
    public List<FacturaResponse> consultarTodas() {
        return repositorioFactura.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    private FacturaResponse convertir(Factura factura) {
        Hospedaje hospedaje = factura.getHospedaje();

        return new FacturaResponse(
                factura.getId(),
                hospedaje.getCliente().getId(),
                hospedaje.getCliente().getNombreCompleto(),
                hospedaje.getHabitacion().getNumeroHabitacion(),
                hospedaje.getHabitacion().getTipoHabitacion(),
                hospedaje.getFechaHoraIngreso(),
                hospedaje.getFechaHoraSalida(),
                factura.getCantidadNoches(),
                factura.getValorNoche(),
                factura.getValorTotal(),
                factura.getFechaGeneracion()
        );
    }
}

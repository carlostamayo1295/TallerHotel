package co.edu.cesde.hotel.servicio;

import co.edu.cesde.hotel.dto.FacturaResponse;
import java.util.List;

public interface ServicioFactura {
    FacturaResponse consultarPorId(Long id);
    List<FacturaResponse> consultarTodas();
}

package co.edu.cesde.hotel.servicio;

import co.edu.cesde.hotel.dto.CheckOutRequest;
import co.edu.cesde.hotel.dto.FacturaResponse;

public interface ServicioCheckOut {
    FacturaResponse realizarCheckOut(CheckOutRequest request);
}

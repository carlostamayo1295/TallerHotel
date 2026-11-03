package co.edu.cesde.hotel.servicio;

import co.edu.cesde.hotel.dto.CheckInRequest;
import co.edu.cesde.hotel.dto.CheckInResponse;

public interface ServicioCheckIn {
    CheckInResponse realizarCheckIn(CheckInRequest request);
}

package co.edu.cesde.hotel.servicio;

import co.edu.cesde.hotel.dto.ClienteRequest;
import co.edu.cesde.hotel.dto.ClienteResponse;
import java.util.List;

public interface ServicioCliente {
    ClienteResponse crearCliente(ClienteRequest request);
    ClienteResponse consultarPorId(Long id);
    ClienteResponse consultarPorIdentificacion(String identificacion);
    List<ClienteResponse> consultarTodos();
}

package co.edu.cesde.hotel.servicio.impl;

import co.edu.cesde.hotel.dto.ClienteRequest;
import co.edu.cesde.hotel.dto.ClienteResponse;
import co.edu.cesde.hotel.entidad.Cliente;
import co.edu.cesde.hotel.excepcion.ClienteNoEncontradoException;
import co.edu.cesde.hotel.repositorio.RepositorioCliente;
import co.edu.cesde.hotel.servicio.ServicioCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioClienteImpl implements ServicioCliente {

    private final RepositorioCliente repositorioCliente;

    @Override
    @Transactional
    public ClienteResponse crearCliente(ClienteRequest request) {
        if (repositorioCliente.existsByIdentificacion(request.identificacion())) {
            throw new DataIntegrityViolationException(
                    "Ya existe un cliente con esa identificación");
        }

        Cliente cliente = new Cliente();
        cliente.setIdentificacion(request.identificacion());
        cliente.setNombreCompleto(request.nombreCompleto());
        cliente.setCorreoElectronico(request.correoElectronico());
        cliente.setTelefono(request.telefono());

        return convertir(repositorioCliente.save(cliente));
    }

    @Override
    public ClienteResponse consultarPorId(Long id) {
        Cliente cliente = repositorioCliente.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe el cliente con ID " + id));

        return convertir(cliente);
    }

    @Override
    public ClienteResponse consultarPorIdentificacion(String identificacion) {
        Cliente cliente = repositorioCliente.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe el cliente con identificación " + identificacion));

        return convertir(cliente);
    }

    @Override
    public List<ClienteResponse> consultarTodos() {
        return repositorioCliente.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    private ClienteResponse convertir(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getIdentificacion(),
                cliente.getNombreCompleto(),
                cliente.getCorreoElectronico(),
                cliente.getTelefono(),
                cliente.getFechaCreacion()
        );
    }
}

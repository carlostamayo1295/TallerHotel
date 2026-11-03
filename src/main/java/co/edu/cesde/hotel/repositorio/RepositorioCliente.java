package co.edu.cesde.hotel.repositorio;

import co.edu.cesde.hotel.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioCliente extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdentificacion(String identificacion);

    boolean existsByIdentificacion(String identificacion);
}

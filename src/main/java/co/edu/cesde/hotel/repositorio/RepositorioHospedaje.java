package co.edu.cesde.hotel.repositorio;

import co.edu.cesde.hotel.entidad.Hospedaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioHospedaje extends JpaRepository<Hospedaje, Long> {

    boolean existsByClienteIdAndActivoTrue(Long clienteId);

    Optional<Hospedaje> findByClienteIdAndActivoTrue(Long clienteId);
}

package co.edu.cesde.hotel.repositorio;

import co.edu.cesde.hotel.entidad.Factura;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RepositorioFactura extends Repository<Factura, Long> {

    Factura save(Factura factura);

    Optional<Factura> findById(Long id);

    List<Factura> findAll();
}
